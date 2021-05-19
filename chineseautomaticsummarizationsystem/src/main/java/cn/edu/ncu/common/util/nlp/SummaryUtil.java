package cn.edu.ncu.common.util.nlp;

import org.lionsoul.jcseg.ISegment;
import org.lionsoul.jcseg.dic.ADictionary;
import org.lionsoul.jcseg.dic.DictionaryFactory;
import org.lionsoul.jcseg.extractor.SummaryExtractor;
import org.lionsoul.jcseg.extractor.impl.TextRankSummaryExtractor;
import org.lionsoul.jcseg.segmenter.SegmenterConfig;
import org.lionsoul.jcseg.sentence.SentenceSeg;

import java.io.IOException;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 18:39 2021/4/14
 * @Modified By:
 */
public class SummaryUtil {
    public static void generateSummary(){
        SegmenterConfig config = new SegmenterConfig(true);
        config.setClearStopwords(true);
        config.setAppendCJKSyn(false);
        config.setKeepUnregWords(false);
        ADictionary dic = DictionaryFactory.createSingletonDictionary(config);

        try {
            ISegment seg = ISegment.COMPLEX.factory.create(config, dic);
            SummaryExtractor extractor = new TextRankSummaryExtractor(seg, new SentenceSeg());

            String doc = "";
            doc = "前两天，李咏的女儿法图麦·李和闺蜜吃烤串，闺蜜在微博中晒两人卖萌合影，并隔空喊话称法图麦“少女你好！”照片中，法图麦披着长长的头发，漂亮的大眼睛，双手托腮卖萌，非常的可爱。"
                    + "随后，法图麦转发此微博，并俏皮的写道：“烤串儿你好！” ▼左为李咏女儿法图麦·李，右为其闺蜜。  "
                    + "妈妈哈文也可爱地转发微博，并喊话女儿，称：“该回家了好不好？”  "
                    + "小编真心惊呆了，这个这个清纯的妹子真的是哈文和李咏的女儿么？ "
                    + "因为小编平时很少关注李咏夫妇，对李咏爱女法图麦的印象还停留在...酱紫↓    翻遍妈妈哈文的微博相册，发现哈文是真心宠爱女儿啊，满满的姑娘的美照，拿出来给大家看看。    "
                    + "与爸爸在一起的照片，小法也是酷劲十足！"
                    + "13岁的小女孩出落得亭亭玉立，与之前判若两人   最后说一句： 我女儿再美也是我生的！  "
                    + "来源：bomb01|新华网|新浪微博";

            List<String> keySentence = null;
//            keySentence = extractor.getKeySentenceFromString(doc);
            keySentence = extractor.getKeySentenceFromFile("D:\\Graduate Designing\\chineseautomaticsummarizationsystem\\src\\main\\resources\\paper_resources\\2.txt");
            System.out.println("+-Key sentence: ");
            int count = 1;
            for ( String sen : keySentence )
            {
                System.out.println("Top " + count + ": " + sen);
                count++;
            }

            //System.out.println("+-Summary: ");
            //String summary = extractor.getSummaryFromString(doc, 100);
            //String summary = extractor.getSummaryFromFile("/home/chenxin/curpos/8.txt", 86);
            //System.out.println(summary);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        generateSummary();
    }
}
