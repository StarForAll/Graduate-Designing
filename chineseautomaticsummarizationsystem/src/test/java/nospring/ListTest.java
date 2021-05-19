package nospring;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 19:51 2021/4/23
 * @Modified By:
 */
public class ListTest {
    @Test
    public void stringSpilt(){
        String keywords="关键词：马克斯·韦伯; 实证主义; 人文主义; 方法论; 调适策略;";
        System.out.println(keywords.indexOf("关键词"));
        System.out.println(getKeywords(keywords));
    }
    public static List<String> getKeywords(String keywordsLine){
        //关键词+冒号去除
        String[] split = keywordsLine.substring(4).split("[;；]");
        List<String> result= new ArrayList<>();
        for(String word:split){
            result.add(word.trim());
        }
        return result;
    }
}
