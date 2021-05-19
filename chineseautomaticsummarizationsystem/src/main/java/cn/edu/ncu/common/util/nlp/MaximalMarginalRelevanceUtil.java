package cn.edu.ncu.common.util.nlp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: XiongZhiCong
 * @Description: mmr算法：最大边缘相关(Maximal Marginal Relevance)，根据λ调节准确性和多样性
 *                      目的是减少排序结果的冗余，同时保证结果的相关性
 * @Date: Created in 18:00 2021/4/20
 * @Modified By:
 */
public class MaximalMarginalRelevanceUtil {
    /**
     * 最大边缘相关阀值
     * MAX_MMR_THRESHOLD
     */
    private static final double MAX_MMR_THRESHOLD = 0.4;
    /**
     * 最大边缘相关(Maximal Marginal Relevance)，根据λ调节准确性和多样性
     * max[λ*score(i) - (1-λ)*max[similarity(i,j)]]:score(i)句子的得分【得分即本句话与整个文章的相似度】，similarity(i,j)句子i与j的相似度
     * User-tunable diversity through λ parameter
     * - High λ= Higher accuracy
     * - Low λ= Higher diversity
     * @param sortedSentList  句子打分并排序好的集合信息
     * @param cosSimilarity 句子间相似度集合
     * @return
     */
    public static Map<Integer, Double> mmr(List<Map.Entry<Integer, Double>> sortedSentList,double[][] cosSimilarity) {
        Map<Integer, Double> sortedLinkedSent = new LinkedHashMap<>();
        for (Map.Entry<Integer, Double> entry : sortedSentList) {
            sortedLinkedSent.put(entry.getKey(), entry.getValue());
        }
        //最终的得分（句子编号与得分）
        Map<Integer, Double> mmrSentenceScore = new LinkedHashMap<>();
        //第一步先将最高分的句子加入
        Map.Entry<Integer, Double> highestScore = sortedSentList.get(0);
        mmrSentenceScore.put(highestScore.getKey(), highestScore.getValue());
        boolean flag = true;
        while (flag) {
            int index = 0;
            //通过迭代计算获得最高分句子
            double maxScore = Double.NEGATIVE_INFINITY;
            for (Map.Entry<Integer, Double> entry : sortedLinkedSent.entrySet()) {
                if (mmrSentenceScore.containsKey(entry.getKey())) {
                    continue;
                }
                double simSentence = 0.0;
                //这个是获得最相似的那个句子的最大相似值
                for (Map.Entry<Integer, Double> mmrEntry : mmrSentenceScore.entrySet()) {
                    double simSen = 0.0;
                    if (entry.getKey() > mmrEntry.getKey()) {
                        simSen = cosSimilarity[mmrEntry.getKey()][entry.getKey()];
                    } else {
                        simSen = cosSimilarity[entry.getKey()][mmrEntry.getKey()];
                    }
                    if (simSen > simSentence) {
                        simSentence = simSen;
                    }
                }
                simSentence = MAX_MMR_THRESHOLD * entry.getValue() - (1 - MAX_MMR_THRESHOLD) * simSentence;
                if (simSentence > maxScore) {
                    maxScore = simSentence;
                    //句子编号
                    index = entry.getKey();
                }
            }
            mmrSentenceScore.put(index, maxScore);
            if (mmrSentenceScore.size() == sortedLinkedSent.size()) {
                flag = false;
            }
        }
        return mmrSentenceScore;
    }

    /**
     * 每个句子的相似度，这里使用简单的jaccard方法，计算所有句子的两两相似度
     * 以上只是简单的从字符出现或者没有出现的角度进行相似度计算，而没有考虑其他因素。实际上，还可以结合词义进行近义词替换，来进一步达到语义层面的相似度计算，这样结果会更为准确。
     * 使用了余弦相似度计算算法 
     * @param sentSegmentWords 每个句子包含分词列表
     * @return
     */
    public static double[][] getCosSimilarity(Map<Integer, List<String>> sentSegmentWords,List<String> stopWords) {
        int size = sentSegmentWords.size();
        double[][] simSent = new double[size][size];
        for (Map.Entry<Integer, List<String>> entry : sentSegmentWords.entrySet()) {
            for (Map.Entry<Integer, List<String>> entry1 : sentSegmentWords.entrySet()) {
                if (entry.getKey() >= entry1.getKey()) {
                    continue;
                }
                //去除停用词
                List<String> words1 = entry1.getValue();
                words1=words1.stream().filter(word->!stopWords.contains(word)).collect(Collectors.toList());
                List<String> words = entry.getValue();
                words=words.stream().filter(word->!stopWords.contains(word)).collect(Collectors.toList());
                simSent[entry.getKey()][entry1.getKey()] = SimilarityCalculate.getSimilarity(words1,words);
            }
        }
        return simSent;
    }
}
