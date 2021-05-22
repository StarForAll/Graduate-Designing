package cn.edu.ncu.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 角色VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class RoleVO {

    @ApiModelProperty("角色ID")
    private Long id;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("角色备注")
    private String remark;
}
