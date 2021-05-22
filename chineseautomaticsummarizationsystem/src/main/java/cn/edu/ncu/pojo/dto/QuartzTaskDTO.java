package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.constant.TaskStatusEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class QuartzTaskDTO {

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("任务名称")
    @NotNull(message = "任务名称不能为空")
    private String taskName;

    @ApiModelProperty("任务Bean")
    @NotNull(message = "任务Bean不能为空")
    private String taskBean;

    @ApiModelProperty("任务参数")
    private String taskParams;

    @ApiModelProperty("cron")
    @NotNull(message = "cron表达式不能为空")
    private String taskCron;

    @ApiModelProperty("任务状态:"+ TaskStatusEnum.INFO)
    private Integer taskStatus;

    @ApiModelProperty("任务备注")
    private String remark;
}
