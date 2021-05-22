package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 角色查询DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class RoleQueryDTO extends PageParamDTO {

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色id")
    private String roleId;
}
