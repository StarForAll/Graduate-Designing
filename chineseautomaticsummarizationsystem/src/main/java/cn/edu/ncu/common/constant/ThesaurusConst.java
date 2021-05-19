package cn.edu.ncu.common.constant;

import cn.edu.ncu.dao.entity.SentenceIdentification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: XiongZhiCong
 * @Description: 词库类别
 * @Date: Created in 16:53 2021/4/13
 * @Modified By:
 */
public class ThesaurusConst {
    /**
     * 所有类别
     */
    public static final int ALL_WORD = -1;
    /**
     * 停用词
     */
    public static final int STOP_WORD = 0;
    /**
     * 疑问词
     */
    public static final int INTERROGATIVE_WORD =1;
    /**
     * 疑问句正则表达式
     * REGULAR_EXPRESSION_WORD
     */
    public static final int REGULAR_EXPRESSION_WORD =2;
    /**
     * 复句结束符号
     */
    public static final int COMPLEX_SENTENCE_WORD =3;
    /**
     * 分句结束符号
     */
    public static final int CLAUSE_WORD =4;
    /**
     * 对于标题来说无意义的词
     * 标题中和论文无关的词
     */
    public static final int MEANINGLESS_WORD =5;
    /**
     * 章节类型
     */
    public static final int CHAPTER_TYPE =0;
    /**
     * 小标题类型
     */
    public static final int SUBHEAD_TYPE =1;
    /**
     * 句子类型
     */
    public static final int SENTENCE_TYPE =2;
    /**
     * 章节对应的段落index
     */
    public static final int CHAPTER_TO_PARAGRAPH =0;
    /**
     * 文章标题
     */
    public static final SentenceIdentification TITLE_SENTENCE =new SentenceIdentification(0,0,0,0);
    private static final String SENTENCE_REGULAR="[[a-zA-Z）)\\]】[0-9]　 \u4e00-\u9fa5]]([;,:；，：。！？?.!]|\\.{6})(?!([0-9]).)";
    public static void main(String[] args) {
        /**
         * 四甲基联苯胺显现血痕具有很好的效3.1.4 无色结晶紫显现法  无色结 晶紫(leucocrystal violet ，LCV ）是一种对纤维素和蛋 白质具有亲和力的阳离子染料 ，
         * 两种配方见表 1。Grodsky使诺 、碳酸钠和过硼酸钠配置鲁米诺试剂 ，
         * “仿人,选突破口,先易后难,稳步迈进,坚持不懈” ,这几乎就是我们的全部决窍!
         */
        String lineText="两种配方见表 1。Grodsky使诺 、碳酸钠和过硼酸钠配置鲁米诺试剂 ，";
        Pattern pa=Pattern.compile(SENTENCE_REGULAR);
        Matcher ma=pa.matcher(lineText);
        int lastIndexOf = 0;
        while (ma.find()){
            int indexOf=ma.start()+2;
            String line=lineText.substring(lastIndexOf,indexOf);
            System.out.println(line);
            lastIndexOf=indexOf;
        }
    }
}
