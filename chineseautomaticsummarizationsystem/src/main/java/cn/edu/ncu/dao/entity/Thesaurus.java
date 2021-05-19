package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.*;

@ApiModel(value="cn.edu.ncu.dao.entity.Thesaurus")
@Table(name = "thesaurus")
public class Thesaurus extends BaseEntity {
    public static final String WORD_PREFIX="Thesaurus_Word";
    /**
     * 词
     */
    @ApiModelProperty(value="word词")
    private String word;

    /**
     * 词的种类
     */
    @ApiModelProperty(value="type词的种类")
    private Integer type;

    /**
     * 词
     */
    public String getWord() {
        return word;
    }

    /**
     * 词
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * 词的种类
     */
    public Integer getType() {
        return type;
    }

    /**
     * 词的种类
     */
    public void setType(Integer type) {
        this.type = type;
    }
}