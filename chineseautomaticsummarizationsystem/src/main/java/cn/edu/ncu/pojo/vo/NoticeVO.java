package cn.edu.ncu.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 通知VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class NoticeVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("消息标题")
    private String title;


    @ApiModelProperty("消息创建人")
    private Long createUser;

    private Integer sendStatus;

    @ApiModelProperty("消息创建人名称")
    private String createUserName;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

}
