package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围角色DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class DataScopeBatchSetRoleDTO {

    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    private Long roleId;

    @ApiModelProperty("设置信息")
    @Valid
    private List<DataScopeBatchSetDTO> batchSetList;
}
