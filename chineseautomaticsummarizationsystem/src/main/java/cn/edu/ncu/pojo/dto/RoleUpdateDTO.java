package cn.edu.ncu.pojo.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 角色更新修改DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class RoleUpdateDTO extends RoleAddDTO {

    /**
     * 角色id
     */
    @ApiModelProperty("角色id")
    @NotNull(message = "角色id不能为空")
    protected Long id;


}
