package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务查询DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class QuartzLogQueryDTO extends PageParamDTO {

    @ApiModelProperty(value = "任务Id(不能为空)")
    @NotNull(message = "任务Id不能为空")
    private Integer taskId;
}
