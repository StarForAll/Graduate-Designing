package cn.edu.ncu.common.util.nlp;

import java.io.IOException;
import java.util.*;

/**
 * @Author: XiongZhiCong
 * @Description: 句子打分
 * @Date: Created in 17:40 2021/4/20
 * @Modified By:
 */
public class SentenceScoreUtil {
    /**
     * 关键词间的距离阀值
     */
    private static final int CLUSTER_THRESHOLD = 5;
    /**
     * 给所有句子打分
     * @param sentences 所有句子
     * @param keyWords 关键词
     * @param sentSegmentWords  每个句子包含的分词列表
     * @return
     * @throws IOException
     */
    public static Map<Integer, Double> scoreSentences(List<String> sentences, List<String> keyWords,Map<Integer, List<String>> sentSegmentWords) throws IOException {
        //句子下标，得分
        Map<Integer, Double> scoresMap = new LinkedHashMap<>();
        //每一个句子在list中对应的下标
        int sentenceIndex = -1;
        for (String sentence : sentences) {
            sentenceIndex += 1;
            //对每个句子分词
            List<String> words = SegmentUtil.segment(sentence);
            sentSegmentWords.put(sentenceIndex, words);
            //每个关键词在本句子中的位置
            List<Integer> wordsIndex = new ArrayList<>();
            for (String word : keyWords) {
                if (words.contains(word)) {
                    wordsIndex.add(words.indexOf(word));
                }
            }
            //没有遇到关键词
            if (wordsIndex.size() == 0) {
                continue;
            }
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
    private static void testGetStatic(Map<Integer, List<String>> sentSegmentWord){
        add(sentSegmentWord);
    }
    private static void add(Map<Integer, List<String>> sentSegmentWord){
        sentSegmentWord.put(1,new ArrayList<>());
    }
    public static void main(String[] args) {
        Map<Integer, List<String>> sentSegmentWord=new HashMap<>();
        testGetStatic(sentSegmentWord);
        System.out.println(sentSegmentWord.size());
    }
}
