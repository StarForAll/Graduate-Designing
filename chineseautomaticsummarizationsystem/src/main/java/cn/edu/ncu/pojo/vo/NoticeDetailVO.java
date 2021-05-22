package cn.edu.ncu.pojo.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 通知详情VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class NoticeDetailVO extends NoticeVO {


    @ApiModelProperty("消息内容")
    private String content;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}
