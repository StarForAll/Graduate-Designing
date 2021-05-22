package cn.edu.ncu.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 心跳记录日志VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class HeartBeatRecordVO implements Serializable {

    private Long id;

    @ApiModelProperty("项目名字")
    private String projectPath;

    @ApiModelProperty("服务器ip")
    private String serverIp;

    @ApiModelProperty("进程号")
    private Integer processNo;

    @ApiModelProperty("进程开启时间")
    private Date processStartTime;

    @ApiModelProperty("心跳当前时间")
    private Date heartBeatTime;


}
