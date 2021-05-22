package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 部门更新DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class DepartmentUpdateDTO extends DepartmentCreateDTO {

    @ApiModelProperty("部门id")
    @NotNull(message = "部门id不能为空")
    private Long id;

}
