package cn.edu.ncu.common.util.nlp;

import cn.edu.ncu.common.core.exception.BusinessException;

import java.io.IOException;
import java.util.*;

/**
 * @Author: XiongZhiCong
 * @Description: 两个句子间相似度计算
 * 采用的相似度计算方法是余弦相似度计算
 * yú xián
 * @Date: Created in 13:48 2021/4/20
 * @Modified By:
 */
public class SimilarityCalculate {
    private static final double THRESHOLD = 0.2;

    /**
     * 停用词已经过滤
     * @param sentence1 分词列表1
     * @param sentence2 分词列表2
     * @return 两句间的余弦相似度
     */
    public static double getSimilarity(List<String> sentence1, List<String> sentence2) {
        int size, size2;
        if ( sentence1 != null && ( size = sentence1.size() ) > 0 && sentence2 != null && ( size2 = sentence2.size() ) > 0 ) {
            //sentence1和sentence2的并集sentences
            Map<String, double[]> sentences = new HashMap<>();
            String index;
            for ( int i = 0 ; i < size ; i++ ) {
                index = sentence1.get(i) ;
                if( index != null){
                    double[] c = new double[2];
                    //sentence1的语义分数Ci
                    c[0] = 1;
                    //sentence2的语义分数Ci
                    c[1] = THRESHOLD;
                    sentences.put( index, c );
                }
            }

            for ( int i = 0; i < size2 ; i++ ) {
                index = sentence2.get(i) ;
                if( index != null ){
                    double[] c = sentences.get( index );
                    if( c != null && c.length == 2 ){
                        //sentence2中也存在，sentence2的语义分数=1
                        c[1] = 1;
                    }else {
                        c = new double[2];
                        //sentence1的语义分数Ci
                        c[0] = THRESHOLD;
                        //sentence2的语义分数Ci
                        c[1] = 1;
                        sentences.put( index , c );
                    }
                }
            }

            //开始计算，百分比
            Iterator<String> it = sentences.keySet().iterator();
            double s1 = 0 , s2 = 0, allSum = 0;
            while( it.hasNext() ){
                double[] c = sentences.get( it.next() );
                allSum += c[0]*c[1];
                s1 += c[0]*c[0];
                s2 += c[1]*c[1];
            }
            //百分比
            return allSum / Math.sqrt( s1*s2 );
        }
        return 0.0;
    }
    public static void main(String[] args) throws IOException {
        String sentence1="我是一位教师";
        String sentence2="我是一名教师";

        List<String> segment1 = SegmentUtil.segment(sentence1);
        List<String> segment2 = SegmentUtil.segment(sentence2);
        System.out.println(getSimilarity(segment1,segment2));
    }
}
