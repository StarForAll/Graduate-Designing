package cn.edu.ncu.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 权限菜单VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PrivilegeMenuVO {

    @ApiModelProperty("菜单名")
    private String menuName;

    @ApiModelProperty("菜单Key")
    private String menuKey;

    @ApiModelProperty("菜单父级Key")
    private String parentKey;

    @ApiModelProperty("顺序")
    private Integer sort;

    @ApiModelProperty("前端路由path")
    private String url;


}
