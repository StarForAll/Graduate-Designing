package cn.edu.ncu.pojo.dto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 角色添加DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class RoleAddDTO {

    /**
     * 角色名称
     */
    @ApiModelProperty("角色名称")
    @NotNull(message = "角色名称不能为空")
    @Length(min = 1, max = 20, message = "角色名称(1-20)个字符")
    private String roleName;

    /**
     * 角色描述
     */
    @ApiModelProperty("角色描述")
    @Length(max = 255, message = "角色描述最多255个字符")
    private String remark;


}
