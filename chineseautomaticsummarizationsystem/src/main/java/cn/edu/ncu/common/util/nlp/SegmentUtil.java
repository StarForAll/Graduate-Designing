package cn.edu.ncu.common.util.nlp;

import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.IWord;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;
import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author: XiongZhiCong
 * @Description: 分词算法
 * @Date: Created in 12:47 2021/4/14
 * @Modified By:
 */
public class SegmentUtil {
    private static ISegment seg;
    static {
        SegmenterConfig config = new SegmenterConfig(true);
        //设置过滤停止词
        config.setClearStopwords(true);
        //设置关闭同义词追加
        config.setAppendCJKSyn(false);
        //设置去除不识别的词条
        config.setKeepUnregWords(false);
        ADictionary dic = DictionaryFactory.createDefaultDictionary(config, true);
        //使用jcseg的复杂模式分词
        seg = ISegment.COMPLEX.factory.create(config, dic);
    }
    /**
     * 使用jcseg中的分词算法实现中文分词
     * 已经去除符号
     * @param text 需要分词的文本
     * @throws IOException
     */
    public static List<String> segment(String text) throws IOException {
        seg.reset(new StringReader(text));
        IWord word;
        //不能去重
        List<String> result=new ArrayList<>();
        while ( (word = seg.next()) != null ) {
            //过滤标点符号
            if(word.getType()!=IWord.T_PUNCTUATION&&word.getType()!=IWord.T_UNRECOGNIZE_WORD) {
                result.add(word.getValue());
            }
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        System.out.println(SegmentUtil.segment("结束语"));
    }
}
