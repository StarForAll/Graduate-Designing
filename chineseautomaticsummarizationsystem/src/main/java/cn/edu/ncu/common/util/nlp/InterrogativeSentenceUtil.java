package cn.edu.ncu.common.util.nlp;

import cn.edu.ncu.common.util.basic.StringUtil;

import java.io.IOException;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 疑问句相关算法
 * @Date: Created in 14:12 2021/4/14
 * @Modified By:
 */
public class InterrogativeSentenceUtil {
    public static boolean isInterrogative(String title,List<String> segment, List<String> interrogates,List<String> interrogativeRegular) throws IOException {
        if(isBelongToInterrogative(segment,interrogates)){
            return true;
        }
        return matchesRegular(title, interrogativeRegular);
    }

    /**
     * 判断标题分词是否全部都属于疑问词库
     * @param segment 标题包含的所有分词
     * @param interrogates 疑问词库
     * @return
     */
    private static boolean isBelongToInterrogative(List<String> segment, List<String> interrogates){
        return interrogates.containsAll(segment);
    }

    /**
     * 判断标题句是否符合疑问句正则表达式
     * @param title 标题句
     * @param interrogativeRegular 疑问句正则表达式集合
     * @return true是疑问句
     */
    private static boolean matchesRegular(String title, List<String> interrogativeRegular){
        String[] masks={"-","—"};
        String[]titles=StringUtil.contains(title,masks[0])?StringUtil.split(title,masks[0]):StringUtil.split(title,masks[1]);
        for (String t:titles){
            for(String match:interrogativeRegular){
                if(t.matches(match)){
                    return true;
                }
            }
        }
        return false;
    }
}
