package cn.edu.ncu.pojo.vo;

import cn.edu.ncu.common.constant.TaskResultEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务记录VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class QuartzTaskLogVO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("任务id")
    private Long taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("任务参数")
    private String taskParams;

    @ApiModelProperty("任务处理状态:"+ TaskResultEnum.INFO)
    private Integer processStatus;

    @ApiModelProperty("任务时长ms")
    private Long processDuration;

    @ApiModelProperty("处理日志")
    private String processLog;

    @ApiModelProperty("创建时间")
    private Date createTime;


    @ApiModelProperty("主机ip")
    private String ipAddress;
}
