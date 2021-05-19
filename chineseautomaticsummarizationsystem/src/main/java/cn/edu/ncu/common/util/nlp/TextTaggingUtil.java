package cn.edu.ncu.common.util.nlp;

import cn.edu.ncu.common.constant.ThesaurusConst;
import cn.edu.ncu.common.util.basic.FileUtil;
import cn.edu.ncu.dao.entity.SentenceIdentification;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.Charsets;
import org.apache.http.entity.ContentType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: XiongZhiCong
 * @Description: 篇章标识算法
 * 将一篇文章标识出章，段，复句，分句的结构
 * @Date: Created in 16:08 2021/4/14
 * @Modified By:
 */
public class TextTaggingUtil {
    /**
     * 标题一定满足：
     * ^[一二三四五六七八九].*[\u4e00-\u9fa5]$
     * ^[1-9].*[\u4e00-\u9fa5]$
     * 分句的正则表达式：
     * ^.*[a-zA-Z）)\u4e00-\u9fa5][;,:；，：].*$
     * 复句的正则表达式：
     * ^.*[a-zA-Z）)\u4e00-\u9fa5][。！？?.!\.{6}].*$
     */
    private static final String TITLE_HAN_REGULAR = "^[一二三四五六七八九]、.*[\u4e00-\u9fa5　 ]$";
    private static final String TITLE_HAN_MORE_REGULAR = "^第[一二三四五六七八九]章.*[\u4e00-\u9fa5　 ]$";
    private static final String SMALL_TITLE_HAN_MORE_REGULAR = "^（[一二三四五六七八九]+）.*$";
    private static final String NOT_TITLE_REGULAR = "^.*[a-zA-Z）)”\u4e00-\u9fa5]([;,:；，：。！？?.!]|\\.{6}).*$";
    private static final String SYMBOL__ENG_REGULAR = "^.*\"([;,:；，：。！？?.!]|\\.{6})\".*$";
    private static final String SYMBOL__HAN_REGULAR = "^.*“([;,:；，：。！？?.!]|”{6})\".*$";
    private static final String TITLE_NUM_REGULAR = "^[0-9].*[\u4e00-\u9fa5　 ]$";
    private static final String CLAUSE_REGULAR = "^.*[a-zA-Z）)\u4e00-\u9fa5][;,:；，：].*$";
    public static final String SENTENCE_REGULAR = "[[a-zA-Z）)\\]】[0-9]　\"” \u4e00-\u9fa5]]([;,:；，：。！？?.!]|\\.{6})(?!(([0-9])\\.))";
    /**
     * 不需要英文句号
     */
    private static final String ONE_COMPLEX_SENTENCE_REGULAR = "([。！？?!]|\\.{6})";
    private static final String COMPLEX_SENTENCE_END_REGULAR = "^.*([。！？?.!]|\\.{6})$";
    private static final String CLAUSE_END_REGULAR = "^.*([;,:；，：])$";
    private static final String COMPLEX_SENTENCE_REGULAR = "^.*[a-zA-Z）)\u4e00-\u9fa5]([。！？?.!]|\\.{6}).*$";
    private static final String SUBHEAD_REGULAR = "^[0-9]+\\.[0-9].*(?!([;,:；，：。！？?.!]|\\.{6})).*[\u4e00-\u9fa5]$";
    private static final String NEW_PARAGRAPH_REGULAR = "^[　 ]{2}.*[a-zA-Z）)\u4e00-\u9fa5]+([;,:；，：。！？?.!]|\\.{6})*.*$";
    private static boolean previousLineIsComplexSentence = true;
    /**
     * 拼接文章内容后面分词需要
     */
    private static String articleContent = "";
    /**
     * 小标题
     */
    private static String subTitle = "";
    private static String keywordsLine="";

    public static void setKeywordsLine(String keywordsLine) {
        TextTaggingUtil.keywordsLine = keywordsLine;
    }

    public static void setPreviousLineIsComplexSentence(boolean previousLineIsComplexSentence) {
        TextTaggingUtil.previousLineIsComplexSentence = previousLineIsComplexSentence;
    }

    public static void setArticleContent(String articleContent) {
        TextTaggingUtil.articleContent = articleContent;
    }

    public static void setSubTitle(String subTitle) {
        TextTaggingUtil.subTitle = subTitle;
    }

    public static void setIsFirstParagraph(boolean isFirstParagraph) {
        TextTaggingUtil.isFirstParagraph = isFirstParagraph;
    }

    public static void setChapterIndex(int chapterIndex) {
        TextTaggingUtil.chapterIndex = chapterIndex;
    }

    public static void setParagraphIndex(int paragraphIndex) {
        TextTaggingUtil.paragraphIndex = paragraphIndex;
    }

    public static void setComplexSentenceIndex(int complexSentenceIndex) {
        TextTaggingUtil.complexSentenceIndex = complexSentenceIndex;
    }

    public static void setClauseIndex(int clauseIndex) {
        TextTaggingUtil.clauseIndex = clauseIndex;
    }


    public static String getSubTitle() {
        return subTitle;
    }

    /**
     * 获取论文本身自带的关键词
     * @return
     */
    public static List<String> getKeywords(){
        List<String> result= new ArrayList<>();
        if(keywordsLine.length()>4){
            //关键词+冒号去除
            String[] split = keywordsLine.substring(4).split("[;；]");
            for(String word:split){
                result.add(word.trim());
            }
        }

        return result;
    }
    /**
     * 新章节的第一个自然段
     */
    private static boolean isFirstParagraph = true;
    private static int chapterIndex = 1, paragraphIndex = 1, complexSentenceIndex = 1, clauseIndex = 1;

    /**
     * 判断字符串类型：句子，章节标题，小标题【】
     * 小标题去除
     *
     * @param lineText               上一行残余+本行字符串
     * @param isContainsPreviousLine 是否包含上一行残余字符串
     * @return
     */
    private static int checkLineType(String lineText, boolean isContainsPreviousLine) {
        if (isContainsPreviousLine) {
            return ThesaurusConst.SENTENCE_TYPE;
        }
        if(lineText.matches(SMALL_TITLE_HAN_MORE_REGULAR)){
            return ThesaurusConst.SUBHEAD_TYPE;
        }
        if (lineText.matches(TITLE_HAN_REGULAR) || lineText.matches(TITLE_HAN_MORE_REGULAR)) {
            return ThesaurusConst.CHAPTER_TYPE;
            //而且不包含";,:；，：。！？?.!"或者是“;,:；，：。！？?.!”
        } else if (lineText.matches(TITLE_NUM_REGULAR)) {
            if (lineText.matches(NOT_TITLE_REGULAR)) {
                return ThesaurusConst.SENTENCE_TYPE;
            }
            if (lineText.matches(SUBHEAD_REGULAR)) {
                return ThesaurusConst.SUBHEAD_TYPE;
            }
            return ThesaurusConst.CHAPTER_TYPE;
        }
        return ThesaurusConst.SENTENCE_TYPE;
    }

    /**
     * 判断是否是新自然段
     *
     * @param lineText               上一行残余+本行字符串
     * @param isContainsPreviousLine 是否包含上一行残余字符串
     * @return 新自然段为true
     */
    private static boolean checkNewParagraph(String lineText, boolean isContainsPreviousLine) {
        //上一句残余
        if (isContainsPreviousLine) {
            return false;
        }
        return lineText.matches(NEW_PARAGRAPH_REGULAR);
    }

    /**
     * 处理级别：句,并返回处理剩余字符串
     * previousLineIsComplexSentence 最后本文本处理的分句是否是句号
     *
     * @param result   map结果
     * @param lineText 要处理的文本
     * @return
     */
    private static String doClauseLine(Map<SentenceIdentification, String> result, String lineText) {
        //截断
        Pattern pa = Pattern.compile(SENTENCE_REGULAR);
        Matcher ma = pa.matcher(lineText);
        int lastIndexOf = 0;
        while (ma.find()) {
            int indexOf = ma.start() + 2;
            String line = lineText.substring(lastIndexOf, indexOf);
            if (previousLineIsComplexSentence) {
                ++complexSentenceIndex;
                clauseIndex = 1;
            } else {
                ++clauseIndex;
            }
            //章，段不变
            result.put(new SentenceIdentification(chapterIndex, paragraphIndex, complexSentenceIndex, clauseIndex), line);
            //判断line的句子类型：分/复句
            previousLineIsComplexSentence = line.matches(COMPLEX_SENTENCE_END_REGULAR);
            lastIndexOf = indexOf;
        }
        return lineText.substring(lastIndexOf).trim();
    }

    /**
     * 获取句子列表
     * @return
     */
    public static List<String> getSentences()  {
        List<String> sentences = new ArrayList<>();
        Pattern pa = Pattern.compile(ONE_COMPLEX_SENTENCE_REGULAR);
        Matcher ma = pa.matcher(articleContent);
        int lastIndexOf = 0;
        while (ma.find()) {
            int indexOf = ma.start() + 1;
            String line = articleContent.substring(lastIndexOf, indexOf).trim();
            if(line.length()>0){
                sentences.add(line);
            }
            lastIndexOf = indexOf;
        }
        return sentences;
    }

    /**
     * 根据全文内容获取全文分词
     * @return
     * @throws IOException
     */
    public static List<String> getWordsByText() throws IOException {
        return SegmentUtil.segment(articleContent);
    }
    /**
     * 文本结构化
     *
     * @param file     上传待处理的文本file对象 编码格式必须是utf-8
     * @param encoding 解析文件的字符集
     * @return
     */
    public static TreeMap<SentenceIdentification, String> textTagged(MultipartFile file, Charset encoding) throws IOException {
        TreeMap<SentenceIdentification, String> result = new TreeMap<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(file.getInputStream(), Charsets.toCharset(encoding)));
        //第一句一定是文章标题
        result.put(ThesaurusConst.TITLE_SENTENCE, bufferedReader.readLine());
        String previousLine = "";
        String lineText;

        boolean isContainsPreviousLine = false;
        boolean isDiscard = true;
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder subTitleBuilder = new StringBuilder();
        while ((lineText = bufferedReader.readLine()) != null) {
            //引言前面部分内容抛弃，暂时无用
            isContainsPreviousLine = !"".equals(previousLine);
            lineText = previousLine + lineText;
            if(lineText.length()==0){
                continue;
            }
            if (chapterIndex == 1 && paragraphIndex == 1 && complexSentenceIndex == 1 && clauseIndex == 1 && isDiscard) {
                if(lineText.indexOf("关键词")==0){
                    keywordsLine=lineText;
                }else if (checkLineType(lineText, isContainsPreviousLine) == ThesaurusConst.CHAPTER_TYPE && lineText.matches("^[(一、)01].*$")) {
                    isDiscard = false;
                    complexSentenceIndex = 0;
                    clauseIndex = 0;
                    result.put(new SentenceIdentification(chapterIndex, ThesaurusConst.CHAPTER_TO_PARAGRAPH, complexSentenceIndex, clauseIndex), lineText);
                }
            } else {
                //如果是标题
                int lineType = checkLineType(lineText, isContainsPreviousLine);
                if (lineType == ThesaurusConst.CHAPTER_TYPE) {
                    ++chapterIndex;
                    isFirstParagraph = true;
                    complexSentenceIndex = 0;
                    clauseIndex = 0;
                    result.put(new SentenceIdentification(chapterIndex, ThesaurusConst.CHAPTER_TO_PARAGRAPH, complexSentenceIndex, clauseIndex), lineText);
                } else if (lineType == ThesaurusConst.SENTENCE_TYPE) {
                    //判断是否是新自然段
                    if (checkNewParagraph(lineText, isContainsPreviousLine)) {
                        //新自然段第一句,操作更改段落index,判断句子有无复句/分句标志,result.put
                        //引言下的第一个自然段
                        paragraphIndex = isFirstParagraph ? 1 : paragraphIndex + 1;
                        isFirstParagraph = false;
                        complexSentenceIndex = 0;
                        clauseIndex = 0;
                    }
                    previousLine = doClauseLine(result, lineText);
                    stringBuilder.append(lineText);
                } else if (lineType == ThesaurusConst.SUBHEAD_TYPE) {
                    //小标题
                    subTitleBuilder.append(lineText);
                }

            }
        }

        subTitle = subTitleBuilder.toString();
        articleContent = stringBuilder.toString();
        return result;
    }

    public static void main(String[] args) throws IOException {
        test1();
//        test4("事实上,方法论问题上的互补性应该是一个不可回避的重大问题,正如美国学者博曼所言,“适当的理论和方法问题不再是如何把一种东西化约为另一种东西,而是如何把它们关联起来,并使其相互作用:有关理论的争论不再是还原论的,而是‘关联性’的。”[2](P198)面对二者之间的方法论之争,马克斯·韦伯的社会学方法论鲜明地体现出这种努力。");
    }

    private static void test1() throws IOException {
        /**
         * https://kns.cnki.net/KXReader/Detail?TIMESTAMP=637540116280979922&DBCODE=CJFD&TABLEName=CJFD2008&FileName=JYRJ200809045&RESULT=1&SIGN=R%2f1XIBDnJDa3Zye%2bn1qbqJKjCCY%3d#
         * https://www.hunanhr.cn/shenqingshudaquan/2019/0725/567388.html
         */
        String filePath = "D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\paper_resources\\9.txt";
        File file = new File(filePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), file.getName(),
                ContentType.APPLICATION_OCTET_STREAM.toString(), fileInputStream);
        //分句合复句不能分开处理 SENTENCE_REGULAR
        TreeMap<SentenceIdentification, String> sentenceIdentificationStringMap = textTagged(multipartFile, StandardCharsets.UTF_8);
        List<String> sentences = getSentences();
//        FileUtil.writeLines("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\paper_resources\\0.txt","UTF-8",sentences,false);
    }

    private static void test2() {
        String lineText = "第一章绪论";
        System.out.println(checkLineType(lineText, false));
        System.out.println(lineText.matches(TITLE_HAN_MORE_REGULAR));
    }

    private static void test3() {
        String value = "qsx字符串asdqwxqsx";
        List<String> list = new ArrayList<>();
        list.add("字符串");
        list.add("asd");
        list.add("qwx");
        list.add("qsx");
        int count = 0;
        for (String titleSeg : list) {
            int index = value.indexOf(titleSeg);
            int num = 0;
            while (index != -1) {
                ++num;
                ++count;
                index = value.indexOf(titleSeg, index + 1);
            }
            System.err.println(num);
        }
        System.out.println(count);
    }
    private static void test4(String sentence){
            List<String> sentences = new ArrayList<>();
            Pattern pa = Pattern.compile(SENTENCE_REGULAR);
            Matcher ma = pa.matcher(sentence);
            int lastIndexOf = 0;
            while (ma.find()) {
                int indexOf = ma.start() + 1;
                String line = sentence.substring(lastIndexOf, indexOf).trim();
                if(line.length()>0){
                    sentences.add(line);
                }
                lastIndexOf = indexOf;
            }
        System.out.println(sentences);
        System.out.println(sentences.size());
    }
}
