package cn.edu.ncu.dao.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.Objects;

/**
 * @Author: XiongZhiCong
 * @Description: 句子标识:章，段，复句，分句
 * 计数从1开始
 * 只需要主章节，不需要小标题。
 * @Date: Created in 17:10 2021/4/14
 * @Modified By:
 */
public class SentenceIdentification implements Comparable<SentenceIdentification> {
    private int chapterIndex;
    private int paragraphIndex;
    private int complexSentenceIndex;
    private int clauseIndex;

    public SentenceIdentification(int chapterIndex, int paragraphIndex, int complexSentenceIndex, int clauseIndex) {
        this.chapterIndex = chapterIndex;
        this.paragraphIndex = paragraphIndex;
        this.complexSentenceIndex = complexSentenceIndex;
        this.clauseIndex = clauseIndex;
    }

    public SentenceIdentification() {
    }

    public int getChapterIndex() {
        return chapterIndex;
    }

    public void setChapterIndex(int chapterIndex) {
        this.chapterIndex = chapterIndex;
    }

    public int getParagraphIndex() {
        return paragraphIndex;
    }

    public void setParagraphIndex(int paragraphIndex) {
        this.paragraphIndex = paragraphIndex;
    }

    public int getComplexSentenceIndex() {
        return complexSentenceIndex;
    }

    public void setComplexSentenceIndex(int complexSentenceIndex) {
        this.complexSentenceIndex = complexSentenceIndex;
    }

    public int getClauseIndex() {
        return clauseIndex;
    }

    public void setClauseIndex(int clauseIndex) {
        this.clauseIndex = clauseIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SentenceIdentification that = (SentenceIdentification) o;
        return chapterIndex == that.chapterIndex &&
                paragraphIndex == that.paragraphIndex &&
                complexSentenceIndex == that.complexSentenceIndex &&
                clauseIndex == that.clauseIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(chapterIndex, paragraphIndex, complexSentenceIndex, clauseIndex);
    }

    @Override
    public String toString() {
        return "{" +
                "chapterIndex=" + chapterIndex +
                ", paragraphIndex=" + paragraphIndex +
                ", complexSentenceIndex=" + complexSentenceIndex +
                ", clauseIndex=" + clauseIndex +
                '}';
    }

    /**
     * 自定义排序规则
     * @param other
     * @return
     */
    @Override
    public int compareTo(@NotNull SentenceIdentification other) {
        int compareChapterIndex=this.chapterIndex-other.chapterIndex;
        if(compareChapterIndex<0){
            return -1;
        }else if(compareChapterIndex>0){
            return 1;
        }else {
            int compareParagraphIndex=this.paragraphIndex-other.paragraphIndex;
            if(compareParagraphIndex<0){
                return -1;
            }else if(compareParagraphIndex>0){
                return 1;
            }else {
                int compareComplexSentenceIndex=this.complexSentenceIndex-other.complexSentenceIndex;
                if(compareComplexSentenceIndex<0){
                    return -1;
                }else if(compareComplexSentenceIndex>0){
                    return 1;
                }else {
                    int compareClauseIndex=this.clauseIndex-other.clauseIndex;
                    if(compareClauseIndex<0){
                        return -1;
                    }else if(compareClauseIndex>0){
                        return 1;
                    }else {
                        return 0;
                    }
                }
            }
        }
    }
}
