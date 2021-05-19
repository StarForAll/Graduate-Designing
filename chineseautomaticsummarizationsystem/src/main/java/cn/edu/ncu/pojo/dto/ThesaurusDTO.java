package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.core.pojo.dto.BaseDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 16:45 2021/4/13
 * @Modified By:
 */
public class ThesaurusDTO extends BaseDTO {
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

    public ThesaurusDTO() {
    }

    public ThesaurusDTO(String word, Integer type) {
        this.word = word;
        this.type = type;
    }
    public String getKey(){
        return word+"-----"+type;
    }
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
