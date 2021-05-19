package function;

import cn.edu.ncu.common.constant.SystemConfigEnum;
import cn.edu.ncu.common.constant.ThesaurusConst;
import cn.edu.ncu.common.util.basic.FileUtil;
import cn.edu.ncu.common.util.basic.ListUtil;
import cn.edu.ncu.common.util.basic.StringUtil;
import cn.edu.ncu.common.util.nlp.SegmentUtil;
import cn.edu.ncu.common.util.nlp.SimilarityCalculate;
import cn.edu.ncu.common.util.nlp.TextTaggingUtil;
import cn.edu.ncu.dao.entity.SentenceIdentification;
import cn.edu.ncu.service.TextSummarizationService;
import cn.edu.ncu.service.ThesaurusService;
import org.apache.http.entity.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: 判断一个句子是否是疑问句
 * 论文标题样例：
 * 批判现实主义思潮与美国平民文学--以马克·吐温的小说为例
 * 从商州到世界--阅读贾平凹札记
 * 文学作品的语料库检索分析--以小说《呼啸山庄》为例
 * 全球价值链重构的经济效应--兼论中美经贸摩擦的影响
 * 因美而孤独———围城中的沈从文和他的〈边城〉
 * “她需要温暖”———重读〈寒夜〉兼与李玲先生商榷
 * 人、法与情
 * 标题进行分词->判断词集是否全部都属于疑问词库->如果不是,判断是否符合疑问正则表达式.
 * 获取疑问句型论文标题
 * 环球嘉年华 到底要从北京 人的兜里掏走多少钱？
 * https://baike.baidu.com/item/%E7%96%91%E9%97%AE%E8%AF%8D/389258?fr=aladdin
 * http://xh.5156edu.com/page/z5449m5742j20577.html
 * @Date: Created in 16:29 2021/4/13
 * @Modified By:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = cn.edu.ncu.ChineseAutomaticSummarizationSystem.class)
@EnableCaching
public class CheckInterrogativeSentence {
    @Autowired
    private TextSummarizationService textSummarizationService;
    @Autowired
    private ThesaurusService thesaurusService;

    @Test
    public void testTextSummary() throws IOException {
        String filePath = "D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\paper_resources\\2.txt";
        File file = new File(filePath);
        MultipartFile multipartFile = FileUtil.fileToMultipartFile(file);
        System.out.println(textSummarizationService.getSummaryText(multipartFile));
    }

    @Test
    public void summaryTest() throws IOException {
        newSummary();
    }

    /**
     * 句子编号及分词列表
     */
    Map<Integer, List<String>> sentSegmentWords = null;
    /**
     * 关键词间的距离阀值
     */
    int CLUSTER_THRESHOLD = 5;
    /**
     * 前top-n句子
     */
    int TOP_SENTENCES = 10;
    /**
     * 最大边缘相关阀值
     */
    double λ = 0.4;
    /**
     * 本文章中各个句子间的相似度
     */
    double[][] simSentArray;
    private void newSummary() throws IOException {
        /**
         * 所需要的关键词数量
         */
        int keyWordNum = 50;
        String filePath = "D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\paper_resources\\11.txt";
        File file = new File(filePath);
        MultipartFile multipartFile = FileUtil.fileToMultipartFile(file);
//        List<String> summaryText = textSummarizationService.getSummaryText(multipartFile);
        List<String> summaryText=new ArrayList<>();
        /**
         * 1.获取全文所有的句子
         */
        List<String> sentences = TextTaggingUtil.getSentences();
        /**
         * 2.获取全文所有的分词
         */
        List<String> words = TextTaggingUtil.getWordsByText();
        /**
         * 3.计算单个词在分词列表words中出现的频率
         */
        Map<String, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            Integer val = wordsMap.get(word);
            wordsMap.put(word, val == null ? 1 : val + 1);
        }
        /**
         * 4.过滤停用词以及无意义的词
         */
        List<String> meaninglessWords = thesaurusService.getWordsByType(ThesaurusConst.MEANINGLESS_WORD);
        List<String> stopWords = thesaurusService.getWordsByType(ThesaurusConst.STOP_WORD);
        wordsMap = wordsMap.entrySet().stream()
                .filter((e) -> !meaninglessWords.contains(e.getKey()) && !stopWords.contains(e.getKey()))
                .collect(Collectors.toMap(
                        (e) -> (String) e.getKey(),
                        Map.Entry::getValue
                ));
        /**
         * 5.获取前keyWordNum个关键词
         */
        List<Map.Entry<String, Integer>> wordSortList = new ArrayList<>(wordsMap.entrySet());
        wordSortList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        System.err.println(wordSortList);
        int size = wordSortList.size();
        keyWordNum = Math.min(keyWordNum, size);
        List<String> keyWords = new ArrayList<>();
        for (int i = 0; i < keyWordNum; ++i) {
            keyWords.add(wordSortList.get(i).getKey());
        }

        if (summaryText != null)
            keyWords.addAll(summaryText);
        //去重
        keyWords = ListUtil.removeDuplication(keyWords);
        /**
         * 6.对每个句子进行打分并根据分数进行降序排序
         */
        Map<Integer, Double> scoresLinkedMap = scoreSentences(sentences, keyWords);
        List<Map.Entry<Integer, Double>> sortedSentList = new ArrayList<>(scoresLinkedMap.entrySet());
        sortedSentList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        Map<Integer, Double> MMR_SentScore = mmr(sortedSentList);
        methodOut(sortedSentList, sentences);
        method2Out(sentences, MMR_SentScore);
        method3ut(sortedSentList,sentences,MMR_SentScore);
        //所有的TextTaggingUtil静态变量初始化
    }

    private void methodOut(List<Map.Entry<Integer, Double>> sortedSentList, List<String> sentences) throws IOException {
        TreeMap<Integer, String> keySentence = new TreeMap<>(Integer::compareTo);
        int count = 0;
        for (Map.Entry<Integer, Double> entry : sortedSentList) {
            count++;
            keySentence.put(entry.getKey(), sentences.get(entry.getKey()));
            if (count == TOP_SENTENCES)
                break;
        }
        FileUtil.writeLines("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\sql\\demo.txt","UTF-8",keySentence.values(),false);
        FileUtil.write("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\sql\\demo.txt","\n","UTF-8",true);
    }

    private void method2Out(List<String> sentences, Map<Integer, Double> MMR_SentScore) throws IOException {
        TreeMap<Integer, String> keySentence = new TreeMap<>(Integer::compareTo);
        int count = 0;

        for (Map.Entry<Integer, Double> entry : MMR_SentScore.entrySet()) {
            count++;
            keySentence.put(entry.getKey(), sentences.get(entry.getKey()));
            if (count == TOP_SENTENCES)
                break;
        }
        FileUtil.writeLines("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\sql\\demo.txt","UTF-8",keySentence.values(),true);
        FileUtil.write("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\sql\\demo.txt","\n","UTF-8",true);
//        System.out.println("3:" + getSummarySentence(keySentence));
    }

    /**
     * 综合输出
     *
     * @param sentences
     * @param MMR_SentScore
     */
    private void method3ut(List<Map.Entry<Integer, Double>> sortedSentList, List<String> sentences, Map<Integer, Double> MMR_SentScore) throws IOException {
        //MMR_SentScore:排序
        int oneMethodNums=TOP_SENTENCES / 2;
        List<Map.Entry<Integer, Double>> sortedMMRList = new ArrayList<>(MMR_SentScore.entrySet());
        sortedMMRList.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        TreeMap<Integer, String> keySentence = new TreeMap<>(Integer::compareTo);
        int mmrIndex = 0,sortedSentIndex=0;
        while(keySentence.size()<=TOP_SENTENCES){
            for (int i=sortedSentIndex;i<sortedSentList.size();++i) {
                Map.Entry<Integer, Double> entry = sortedSentList.get(i);
                keySentence.put(entry.getKey(), sentences.get(entry.getKey()));
                if(i-sortedSentIndex>= oneMethodNums){
                    sortedSentIndex=i;
                    break;
                }
                if(keySentence.size()>TOP_SENTENCES){
                    break;
                }
            }
            if(keySentence.size()>TOP_SENTENCES){
                break;
            }
            for(int i=mmrIndex;i<sortedMMRList.size();++i){
                Map.Entry<Integer, Double> entry = sortedMMRList.get(i);
                keySentence.put(entry.getKey(), sentences.get(entry.getKey()));
                if(i-mmrIndex>= oneMethodNums){
                    mmrIndex=i;
                    break;
                }
                if(keySentence.size()>TOP_SENTENCES){
                    break;
                }
            }
        }
        Set<Integer> sentenceIndexes = keySentence.keySet();
        for(Integer sentenceIndex1:sentenceIndexes){
            for(Integer sentenceIndex2:sentenceIndexes){
                double score=simSentArray[sentenceIndex1][sentenceIndex2];
                if (sentenceIndex1< sentenceIndex2&score>0.7){
                    System.err.println("sentence "+sentences.get(sentenceIndex1)+" to sentence "+sentences.get(sentenceIndex2)+" 相似度:"+score);
                }
            }
        }
        FileUtil.writeLines("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\sql\\demo.txt","UTF-8",keySentence.values(),true);
        FileUtil.write("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\sql\\demo.txt","\n","UTF-8",true);
    }

    private String getSummarySentence(TreeMap<Integer, String> keySentence) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer key : keySentence.keySet()) {
            stringBuilder.append(keySentence.get(key));
        }
        return stringBuilder.toString();
    }

    /**
     * 最大边缘相关(Maximal Marginal Relevance)，根据λ调节准确性和多样性
     * max[λ*score(i) - (1-λ)*max[similarity(i,j)]]:score(i)句子的得分，similarity(i,j)句子i与j的相似度
     * User-tunable diversity through λ parameter
     * - High λ= Higher accuracy
     * - Low λ= Higher diversity
     *
     * @param sortedSentList 排好序的句子，编号及得分
     * @return
     */

    private Map<Integer, Double> mmr(List<Map.Entry<Integer, Double>> sortedSentList) {
        simSentArray= sentJSimilarity();//所有句子的相似度
        Map<Integer, Double> sortedLinkedSent = new LinkedHashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : sortedSentList) {
            sortedLinkedSent.put(entry.getKey(), entry.getValue());
        }
        Map<Integer, Double> MMR_SentScore = new LinkedHashMap<Integer, Double>();//最终的得分（句子编号与得分）
        Map.Entry<Integer, Double> Entry = sortedSentList.get(0);//第一步先将最高分的句子加入
        MMR_SentScore.put(Entry.getKey(), Entry.getValue());
        boolean flag = true;
        while (flag) {
            int index = 0;
            double maxScore = Double.NEGATIVE_INFINITY;//通过迭代计算获得最高分句子
            for (Map.Entry<Integer, Double> entry : sortedLinkedSent.entrySet()) {
                if (MMR_SentScore.containsKey(entry.getKey())) continue;
                double simSentence = 0.0;
                for (Map.Entry<Integer, Double> MMREntry : MMR_SentScore.entrySet()) {//这个是获得最相似的那个句子的最大相似值
                    double simSen = 0.0;
                    if (entry.getKey() > MMREntry.getKey())
                        simSen = simSentArray[MMREntry.getKey()][entry.getKey()];
                    else
                        simSen = simSentArray[entry.getKey()][MMREntry.getKey()];
                    if (simSen > simSentence) {
                        simSentence = simSen;
                    }
                }
                simSentence = λ * entry.getValue() - (1 - λ) * simSentence;
                if (simSentence > maxScore) {
                    maxScore = simSentence;
                    index = entry.getKey();//句子编号
                }
            }
            MMR_SentScore.put(index, maxScore);
            if (MMR_SentScore.size() == sortedLinkedSent.size())
                flag = false;
        }
        System.out.println("MMR_SentScore:" + MMR_SentScore);
        return MMR_SentScore;
    }

    /**
     * 每个句子的相似度，这里使用简单的jaccard方法，计算所有句子的两两相似度
     * 以上只是简单的从字符出现或者没有出现的角度进行相似度计算，而没有考虑其他因素。实际上，还可以结合词义进行近义词替换，来进一步达到语义层面的相似度计算，这样结果会更为准确。
     * @return
     */
    private double[][] sentJSimilarity() {
        int size = sentSegmentWords.size();
        double[][] simSent = new double[size][size];
        for (Map.Entry<Integer, List<String>> entry : sentSegmentWords.entrySet()) {
            for (Map.Entry<Integer, List<String>> entry1 : sentSegmentWords.entrySet()) {
                if (entry.getKey() >= entry1.getKey()) continue;
//                int commonWords = 0;
//                double sim = 0.0;
//                for (String entryStr : entry.getValue()) {
//                    if (entry1.getValue().contains(entryStr))
//                        commonWords++;
//                }
//                sim = 1.0 * commonWords / (entry.getValue().size() + entry1.getValue().size() - commonWords);
                simSent[entry.getKey()][entry1.getKey()] = SimilarityCalculate.getSimilarity(entry1.getValue(),entry.getValue());
            }
        }
        return simSent;
    }

    /**
     * 每个句子得分  (keywordsLen*keywordsLen/totalWordsLen)
     *
     * @param sentences 分句
     * @param topnWords keywords top-n关键词
     * @return
     */
    private Map<Integer, Double> scoreSentences(List<String> sentences, List<String> topnWords) throws IOException {
        //句子下标，得分
        Map<Integer, Double> scoresMap = new LinkedHashMap<>();
        sentSegmentWords = new HashMap<>();
        //每一个句子在list中对应的下标
        int sentenceIndex = -1;
        for (String sentence : sentences) {
            sentenceIndex += 1;
            //对每个句子分词
            List<String> words = SegmentUtil.segment(sentence);
            sentSegmentWords.put(sentenceIndex, words);
            //每个关键词在本句子中的位置
            List<Integer> wordsIndex = new ArrayList<>();
            for (String word : topnWords) {
                if (words.contains(word)) {
                    wordsIndex.add(words.indexOf(word));
                }
            }
            //没有遇到关键词
            if (wordsIndex.size() == 0)
                continue;
            Collections.sort(wordsIndex);
            //对于两个连续的单词，利用单词位置索引，通过距离阀值计算一个簇【根据本句中的关键词的距离存放多个词簇】
            List<List<Integer>> clusters = new ArrayList<>();
            List<Integer> cluster = new ArrayList<>();
            cluster.add(wordsIndex.get(0));
            int i = 1;
            while (i < wordsIndex.size()) {
                int wordIndex = wordsIndex.get(i);
                if ((wordIndex - wordsIndex.get(i - 1)) >= CLUSTER_THRESHOLD) {
                    clusters.add(cluster);
                    cluster = new ArrayList<>();
                }
                cluster.add(wordIndex);
                ++i;
            }
            clusters.add(cluster);
            //对每个词簇打分，选择最高得分作为本句的得分
            double maxClusterScore = 0.0;
            for (List<Integer> clu : clusters) {
                //本簇中关键词个数
                int keywordsLen = clu.size();
                //本簇中词的个数
                int totalWordsLen = clu.get(keywordsLen - 1) - clu.get(0) + 1;
                double score = 1.0 * keywordsLen * keywordsLen / totalWordsLen;
                maxClusterScore = Math.max(score, maxClusterScore);
            }
            scoresMap.put(sentenceIndex, maxClusterScore);
        }
        return scoresMap;
    }
}
