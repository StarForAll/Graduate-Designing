package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: XiongZhiCong
 * @Description: 通知添加DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class NoticeAddDTO {

    @ApiModelProperty("消息标题")
    @Length(max = 200)
    private String title;

    @ApiModelProperty("消息内容")
    @Length(max = 5000)
    private String content;

}
