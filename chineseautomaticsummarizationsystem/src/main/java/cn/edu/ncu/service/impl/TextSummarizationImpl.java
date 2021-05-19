package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.*;
import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.common.util.basic.BigDecimalUtil;
import cn.edu.ncu.common.util.basic.FileUtil;
import cn.edu.ncu.common.util.basic.ListUtil;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.common.util.nlp.*;
import cn.edu.ncu.common.util.web.RequestTokenUtil;
import cn.edu.ncu.dao.entity.Abstract;
import cn.edu.ncu.dao.entity.AbstractInfo;
import cn.edu.ncu.dao.entity.File;
import cn.edu.ncu.dao.entity.SentenceIdentification;
import cn.edu.ncu.dao.mapper.AbstractMapper;
import cn.edu.ncu.dao.mapper.FileMapper;
import cn.edu.ncu.pojo.bo.RequestTokenBO;
import cn.edu.ncu.pojo.delete.AbstractInfoDelete;
import cn.edu.ncu.pojo.dto.FileAddDTO;
import cn.edu.ncu.pojo.dto.SystemConfigDTO;
import cn.edu.ncu.pojo.dto.TextSummaryLoadParamDTO;
import cn.edu.ncu.pojo.query.AbstractInfoQuery;
import cn.edu.ncu.pojo.vo.UploadVO;
import cn.edu.ncu.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 16:48 2021/4/16
 * @Modified By:
 */
@Service
@Slf4j
public class TextSummarizationImpl implements TextSummarizationService {
    /**
     * 标题词集和firstSegments交集的势的阈值
     */
    private static final int THRESHOLD = 3;
    /**
     * 要输出的文摘中的句子数量
     */
    private static final int OUT_SENTENCE_NUM = 10;
    /**
     * 两两句子间所允许的最大相似度
     */
    private static final double SIMILARITY_THRESHOLD = 0.7;
    private static final String ABSTRACT_TYPE_INFO = "此标题为抽象型标题";
    private static final String CONCRETE_TYPE_INFO = "此标题为具体型标题";
    @Autowired
    private ThesaurusService thesaurusService;
    @Autowired
    private java.util.Map<String, IFileService> fileServiceMap;
    @Autowired
    private FileServiceAliYun fileServiceAliYun;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private AbstractMapper abstractMapper;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Autowired
    private SystemConfigService systemConfigService;
    @Override
    public ResponseDTO<TextSummaryLoadParamDTO> loadParams(){
        TextSummaryLoadParamDTO textSummaryLoadParamDTO = new TextSummaryLoadParamDTO();
        systemConfigService.getCacheByKey(SystemConfigEnum.Key.UPLOAD_PAPER_IMAGE).getConfigValue();
        textSummaryLoadParamDTO.setPaperExampleImgUrl(systemConfigService.getCacheByKey(SystemConfigEnum.Key.UPLOAD_PAPER_IMAGE).getConfigValue());
        textSummaryLoadParamDTO.setPaperExampleTxtId(Long.valueOf(systemConfigService.getCacheByKey(SystemConfigEnum.Key.UPLOAD_PAPER_TXT).getConfigValue()));
        return ResponseDTO.succData(textSummaryLoadParamDTO);
    }
    @Override
    public ResponseDTO<PageResultDTO<AbstractInfo>> queryAbstractInfo(AbstractInfoQuery abstractInfoQuery) {
//        abstractInfoQuery.setUserId(RequestTokenUtil.getRequestUser().getRequestUserId()); // 测试关闭
        abstractInfoQuery.setUserId(1L);
        abstractInfoQuery.setModuleType(String.valueOf(FileModuleTypeEnum.TEXT_SUMMARY.getValue()));
        PageResultDTO<AbstractInfo> pageResultDTO = new PageResultDTO<>();
        int total=0;
        Boolean searchCount = abstractInfoQuery.getSearchCount();
        if(searchCount ==null||searchCount){
            total=abstractMapper.queryCount(abstractInfoQuery);
        }
        long pages=total/abstractInfoQuery.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(abstractInfoQuery.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(abstractInfoQuery.getPageSize()));
        pageResultDTO.setList(abstractMapper.selectInfoByQuery(abstractInfoQuery));
        return ResponseDTO.succData(pageResultDTO);
    }
    @Override
    public ResponseDTO<String> deleteAbstractInfo(AbstractInfoDelete abstractInfoDelete){
        Long fileId = abstractInfoDelete.getFileId();
        File file = fileMapper.selectByPrimaryKey(fileId);
        if(file==null){
            return ResponseDTO.wrap(FileResponseCodeConst.FILE_EMPTY);
        }
        //删除oos
        fileServiceAliYun.deleteFileOSS(file.getFilePath());
        //删除file
        fileMapper.deleteByPrimaryKey(fileId);
        //删除abstract
        abstractMapper.deleteByPrimaryKey(abstractInfoDelete.getId());
        return ResponseDTO.succ();
    }

    @Override
    public ResponseDTO<String> querySummaryContent(Long id) {
        return ResponseDTO.succData(abstractMapper.selectByPrimaryKey(id).getAbstractContent());
    }

    /**
     * --------------------------------
     * 前端显示摘要信息：
     * 标题：xxx
     * 摘要：自动摘要结果
     * ----------------------------------
     *
     * @param file 上传需要分析的文件
     * @return
     * @throws IOException
     */
    @Override
    public ResponseDTO getSummaryText(MultipartFile file) throws IOException {
        //上传文件到OSS并保存记录到数据库
        ResponseDTO<File> fileUploadResponse = fileUpload(file); //测试时关闭
        if (!fileUploadResponse.isSuccess()) {
            return fileUploadResponse;
        }
        //自动文摘第一步：文章结构初始化
        TreeMap<SentenceIdentification, String> sentenceIdentificationStringMap = TextTaggingUtil.textTagged(file, StandardCharsets.UTF_8);
        //获取主题词<------关键词
        List<String> stopWords = thesaurusService.getWordsByType(ThesaurusConst.STOP_WORD);
        List<String> meaninglessWords = thesaurusService.getWordsByType(ThesaurusConst.MEANINGLESS_WORD);
        List<String> themeWords = getKeyWordsByThemeWords(stopWords,meaninglessWords, sentenceIdentificationStringMap);
        //获取全文所有的句子
        List<String> sentences = TextTaggingUtil.getSentences();
        //所需要的关键词数量
        int keyWordNum = 50;
        //全文所有的主题词
        List<String> keyWords = getKeyWords(sentences,themeWords,meaninglessWords,stopWords,keyWordNum);
        //每个句子包含的分词列表
        Map<Integer, List<String>> sentSegmentWords=new HashMap<>();
        //句子打分
        List<Map.Entry<Integer, Double>> sortedSentList= getSortedSentList(sentences,keyWords,sentSegmentWords);
         //本文章中各个句子间的相似度
        double[][] cosSimilarity= MaximalMarginalRelevanceUtil.getCosSimilarity(sentSegmentWords,stopWords);
        //获取mmr算法结果
        Map<Integer, Double> mmrSentenceScore =MaximalMarginalRelevanceUtil.mmr(sortedSentList,cosSimilarity);
        //获取摘要句子并排序
        TreeMap<Integer, String> keySentences=getKeySentences(sortedSentList,sentences,mmrSentenceScore);
        //去重两两相似度较高的句子
        List<String> finalSentence = getFinalSentence(keySentences, cosSimilarity);
        initTagging();
//        FileUtil.writeLines("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\sql\\demo.txt","UTF-8",finalSentence,false); //测试使用
//        return null; // 测试时使用
        return saveTextSummary(fileUploadResponse,sentenceIdentificationStringMap,finalSentence); //测试时关闭
    }

    /**
     * 将TextTagging类中的属性初始化
     */
    private void initTagging(){
        TextTaggingUtil.setChapterIndex(1);TextTaggingUtil.setParagraphIndex(1);TextTaggingUtil.setComplexSentenceIndex(1);TextTaggingUtil.setClauseIndex(1);
        TextTaggingUtil.setIsFirstParagraph(true);TextTaggingUtil.setArticleContent("");TextTaggingUtil.setSubTitle("");TextTaggingUtil.setPreviousLineIsComplexSentence(true);
        TextTaggingUtil.setKeywordsLine("");
    }
    private ResponseDTO saveTextSummary(ResponseDTO<File> fileUploadResponse ,TreeMap<SentenceIdentification, String> sentenceIdentificationStringMap ,List<String> finalSentence ){
        //将文摘写入到数据库中
        long saveFileId = fileUploadResponse.getData().getId();
        Abstract anAbstract = new Abstract();
        anAbstract.setId(idGeneratorUtil.snowflakeId());
        anAbstract.setFileId(saveFileId);
        anAbstract.setTitle(sentenceIdentificationStringMap.firstEntry().getValue());
        //句子间是否需要换行符：前端显示是否需要【待定】
        anAbstract.setAbstractContent(ListUtil.listToString(finalSentence,' '));
        abstractMapper.insert(anAbstract);
        return ResponseDTO.succ();
    }

    /**
     * 在mmr已经获取到相似度和重要性的比较，最后就不需要进行再次的比较了
     * @param keySentences
     * @param cosSimilarity
     * @return
     */
    private List<String> getFinalSentence(TreeMap<Integer, String> keySentences,double[][] cosSimilarity){
        List<String> finalSentence=new ArrayList<>(keySentences.values());
//        Set<Integer> sentenceIndexes = keySentences.keySet();
//        List<String> delLonSentenceIndexes = new ArrayList<>();
//        for(Integer sentenceIndex1:sentenceIndexes){
//            for(Integer sentenceIndex2:sentenceIndexes){
//                double score=cosSimilarity[sentenceIndex1][sentenceIndex2];
//                if (sentenceIndex1< sentenceIndex2&score>SIMILARITY_THRESHOLD){
//                    //去除长的句子
//                    int index=(keySentences.get(sentenceIndex1).length()>keySentences.get(sentenceIndex2).length())?sentenceIndex1:sentenceIndex2;
//                    delLonSentenceIndexes.add(keySentences.get(index));
//
//                    log.error("sentence 1:{}\n sentence 2:{}\n 相似度:{}",keySentences.get(sentenceIndex1),keySentences.get(sentenceIndex2),score);
//                }
//            }
//        }
//        for(String sentence:delLonSentenceIndexes){
//            finalSentence.remove(sentence);
//        }
        return ListUtil.removeDuplication(finalSentence);
    }
    /**
     * 获取摘要的句子
     * @param sortedSentList
     * @param sentences
     * @param mmrSentenceScore
     * @return
     */
    private TreeMap<Integer, String> getKeySentences(List<Map.Entry<Integer, Double>> sortedSentList, List<String> sentences, Map<Integer, Double> mmrSentenceScore) {
        int oneMethodNums=OUT_SENTENCE_NUM / 2;
        List<Map.Entry<Integer, Double>> sortedMMRList = new ArrayList<>(mmrSentenceScore.entrySet());
        sortedMMRList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        TreeMap<Integer, String> keySentence = new TreeMap<>(Integer::compareTo);
        int mmrIndex = 0,sortedSentIndex=0;
        boolean flag=true;
        while(keySentence.size()<OUT_SENTENCE_NUM&&flag){
//            for (int i=sortedSentIndex;i<sortedSentList.size();++i) {
//                Map.Entry<Integer, Double> entry = sortedSentList.get(i);
//                keySentence.put(entry.getKey(), sentences.get(entry.getKey()));
//                if(i-sortedSentIndex>= oneMethodNums){
//                    sortedSentIndex=i;
//                    break;
//                }
//                if(keySentence.size()>OUT_SENTENCE_NUM){
//                    break;
//                }
//            }
//            if(keySentence.size()>OUT_SENTENCE_NUM){
//                break;
//            }
            for(int i=mmrIndex;i<sortedMMRList.size();++i){
                Map.Entry<Integer, Double> entry = sortedMMRList.get(i);
                if(entry.getValue()==0){
                    flag=false;
                    break;
                }
                keySentence.put(entry.getKey(), sentences.get(entry.getKey()));
                if(keySentence.size()>=OUT_SENTENCE_NUM){
                    break;
                }
            }
        }
        return keySentence;
    }

    /**
     * 上传文件到阿里云OSS上,并保存记录
     *
     * @param file 上传文件
     */
    private ResponseDTO<File> fileUpload(MultipartFile file) {
        IFileService aLiYunFileService = fileServiceMap.get(FileServiceTypeEnum.ALI_OSS.getServiceName());
        ResponseDTO<UploadVO> uploadVOResponseDTO = aLiYunFileService.fileUpload(file, FileModuleTypeEnum.TEXT_SUMMARY.getPath());
        UploadVO data = uploadVOResponseDTO.getData();
        String fileName = data.getFileName();
        File dbFile = new File();
        dbFile.setFileName(fileName);
        if (fileMapper.selectCount(dbFile) > 0) {
            return ResponseDTO.wrap(TextSummarizationResponseCodeConst.UPLOAD_FILE_EXIST);
        }
//        RequestTokenBO requestToken = RequestTokenUtil.getRequestUser();  //测试时注释
        File saveFile=new File();
        saveFile.setModuleId("1");
        saveFile.setModuleType(String.valueOf(FileModuleTypeEnum.TEXT_SUMMARY.getValue()));
        saveFile.setFilePath(data.getFilePath());
        saveFile.setFileLocationType(FileServiceTypeEnum.ALI_OSS.getValue());
        saveFile.setFileName(fileName);
        saveFile.setId(idGeneratorUtil.snowflakeId());
        saveFile.setCreaterUser(1L); //测试使用
//        saveFile.setCreaterUser(requestToken.getRequestUserId()); //测试时注释
        saveFile.setCreateTime(new Date());
        fileMapper.insert(saveFile);
        return ResponseDTO.succData(saveFile);
    }

    /**
     * 获取具体类型论文主题的主题词:弥补使用词频得到关键词的不足
     */
    private List<String> getKeyWordsByThemeWords(List<String> stopWords,List<String> meaninglessWords, TreeMap<SentenceIdentification, String> sentenceIdentificationStringMap) throws IOException {
        /**
         * 主题词集
         */
        StringBuilder themeWord = new StringBuilder();
        if (isSpecificTitle(stopWords, sentenceIdentificationStringMap, themeWord)) {
            log.info(CONCRETE_TYPE_INFO);
            //无意义词和停用词过滤

            List<String> themeWords = SegmentUtil.segment(themeWord.toString());
            themeWords = themeWords.stream().filter(themeWord1 -> !meaninglessWords.contains(themeWord1)).collect(Collectors.toList());
            themeWords = themeWords.stream().filter(themeWord1 -> !stopWords.contains(themeWord1)).collect(Collectors.toList());
            return themeWords;
        }
        log.error(ABSTRACT_TYPE_INFO);
        return null;
    }

    /**
     * 判断是否是具体类型的论文标题
     *
     * @param stopWords                       停用词集
     * @param sentenceIdentificationStringMap 文章结构化数据
     * @param themeWord                       标题，小标题,文章主题放一起组成的句子
     * @return
     * @throws IOException
     */
    private boolean isSpecificTitle(List<String> stopWords, TreeMap<SentenceIdentification, String> sentenceIdentificationStringMap, StringBuilder themeWord) throws IOException {
        /**
         * 1.分词处理
         */
        SentenceIdentification secondParagraph = new SentenceIdentification(1, 2, 1, 1);
        boolean isParagraphTwo = sentenceIdentificationStringMap.containsKey(secondParagraph);
        SentenceIdentification lastSentenceIdentification = sentenceIdentificationStringMap.lastKey();
        int lastChapter = lastSentenceIdentification.getChapterIndex(), lastParagraph = lastSentenceIdentification.getParagraphIndex();
        String title = sentenceIdentificationStringMap.firstEntry().getValue();
        /**
         * 记录第1，2，最后一段内容
         */
        StringBuilder sentence = new StringBuilder();

        themeWord.append(title);
        //获取小标题中的标题
        themeWord.append(TextTaggingUtil.getSubTitle());
        for (Map.Entry<SentenceIdentification, String> entry : sentenceIdentificationStringMap.entrySet()) {
            SentenceIdentification key = entry.getKey();
            String value = entry.getValue();
            int chapter = key.getChapterIndex(), paragraph = key.getParagraphIndex();
            boolean isLastParagraph = (chapter == lastChapter && key.getParagraphIndex() == lastParagraph);
            if (paragraph != 0) {
                if (chapter == 1 && paragraph <= 2) {
                    sentence.append(value);
                } else if (!isParagraphTwo && chapter == 2 && paragraph == 1) {
                    sentence.append(value);
                } else if (isLastParagraph) {
                    //最后一段
                    sentence.append(value);
                }
            } else {
                //获取论文大标题
                themeWord.append(value);
            }

        }
        List<String> titleSegments = SegmentUtil.segment(title);
        List<String> firstSegments = SegmentUtil.segment(sentence.toString());
        /**
         * 2.停用词过滤
         */

        titleSegments = titleSegments.stream().filter(titleSegment -> !stopWords.contains(titleSegment)).collect(Collectors.toList());
        firstSegments = firstSegments.stream().filter(firstSegment -> !stopWords.contains(firstSegment)).collect(Collectors.toList());
        /**
         * 3.titleSegments和firstSegments的交集大小判断:超过阈值，标题就不是抽象型标题
         * interrogativeWords:疑问词库
         * interrogativeRegular:疑问句正则表达式集合
         */
        List<String> compareTitleSegments = firstSegments.stream().filter(titleSegments::contains).collect(Collectors.toList());
        List<String> interrogativeWords = thesaurusService.getWordsByType(ThesaurusConst.INTERROGATIVE_WORD);
        List<String> interrogativeRegular = thesaurusService.getWordsByType(ThesaurusConst.REGULAR_EXPRESSION_WORD);
        /**
         * 具体型标题：交集>THRESHOLD
         * 4.判断标题词集是否全部都属于疑问词库
         * 判断是否属于疑问句式 word frequency
         * (!interrogativeWords.containsAll(titleSegments) && !checkRegularExpression(title))
         */
        return (titleSegments.size() < THRESHOLD || compareTitleSegments.size() > THRESHOLD) && !InterrogativeSentenceUtil.isInterrogative(title, titleSegments, interrogativeWords, interrogativeRegular);
    }
    private List<String> getKeyWords(List<String> sentences,List<String> themeWords,List<String> meaninglessWords,List<String> stopWords,int keyWordNum) throws IOException {
        Map<String, Integer> wordsMap=getWordFrequency(meaninglessWords,stopWords);
        List<String> keyWords = getKeyWordsByFrequency(wordsMap, keyWordNum);
        if (themeWords != null) {
            keyWords.addAll(themeWords);
        }
        //加上本论文中已经就有的关键词
        keyWords.addAll(TextTaggingUtil.getKeywords());
        //去重
        return ListUtil.removeDuplication(keyWords);
    }
    /**
     * 计算单个词在分词列表words中出现的频率:计算词频
     * @return
     * @throws IOException
     */
    private Map<String, Integer> getWordFrequency(List<String> meaninglessWords,List<String> stopWords) throws IOException {
        /**
         * 2.获取全文所有的分词
         */
        List<String> words = TextTaggingUtil.getWordsByText();
        Map<String, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            Integer val = wordsMap.get(word);
            wordsMap.put(word, val == null ? 1 : val + 1);
        }
        /**
         * 4.过滤停用词以及无意义的词
         */
        wordsMap = wordsMap.entrySet().stream()
                .filter((e) -> !meaninglessWords.contains(e.getKey()) && !stopWords.contains(e.getKey()))
                .collect(Collectors.toMap(
                        (e) -> (String) e.getKey(),
                        Map.Entry::getValue
                ));
        return wordsMap;
    }

    /**
     * 获取根据频率得到的关键词
     * @param wordsMap 词频
     * @param keyWordNum 根据词频得到的关键词数量
     * @return
     */
    private List<String> getKeyWordsByFrequency(Map<String, Integer> wordsMap,int keyWordNum) {
        List<Map.Entry<String, Integer>> wordSortList = new ArrayList<>(wordsMap.entrySet());
        wordSortList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        int size = wordSortList.size();
        keyWordNum = Math.min(keyWordNum, size);
        List<String> keyWords = new ArrayList<>();
        for (int i = 0; i < keyWordNum; ++i) {
            keyWords.add(wordSortList.get(i).getKey());
        }

        return keyWords;
    }

    /**
     * 对每个句子进行打分并根据分数进行降序排序,并获取
     * @param sentences
     * @param keyWords
     * @param sentSegmentWords
     * @return
     * @throws IOException
     */
    private List<Map.Entry<Integer, Double>> getSortedSentList(List<String> sentences ,List<String> keyWords,Map<Integer, List<String>> sentSegmentWords) throws IOException {
        List<Map.Entry<Integer, Double>> sortedSentList = new ArrayList<>(SentenceScoreUtil.scoreSentences(sentences,keyWords,sentSegmentWords).entrySet());
        sortedSentList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        return sortedSentList;
    }
}
