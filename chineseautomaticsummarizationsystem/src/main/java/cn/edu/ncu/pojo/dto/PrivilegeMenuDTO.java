package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 权限菜单DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PrivilegeMenuDTO {

    @NotNull
    private Integer type;

    @ApiModelProperty("菜单名")
    @NotNull(message = "菜单名不能为空")
    private String menuName;

    @ApiModelProperty("菜单Key")
    @NotNull(message = "菜单Key不能为空")
    private String menuKey;

    @ApiModelProperty("父级菜单Key,根节点不传")
    private String parentKey;

    @ApiModelProperty("前端路由path")
    @NotNull(message = "前端路由path不能为空")
    private String url;

    @ApiModelProperty("排序字段")
    @NotNull(message = "菜单项顺序不能为空")
    private Integer sort;
}
