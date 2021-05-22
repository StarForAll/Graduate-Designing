package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class DataScopeBatchSetDTO {

    @ApiModelProperty("数据范围类型")
    @NotNull(message = "数据范围类型不能为空")
    private Integer dataScopeType;

    @ApiModelProperty("可见范围")
    @NotNull(message = "可见范围不能为空")
    private Integer viewType;
}
