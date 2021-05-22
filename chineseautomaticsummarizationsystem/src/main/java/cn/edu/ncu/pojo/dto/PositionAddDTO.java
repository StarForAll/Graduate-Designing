package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位添加DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PositionAddDTO {

    /**
     * 岗位名称
     */
    @ApiModelProperty("岗位名称")
    @NotBlank(message = "岗位名称不能为空")
    private String positionName;

    /**
     * 岗位描述
     */
    @ApiModelProperty("岗位描述")
    private String remark;
}
