package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 角色功能权限
 *
 * @author listen
 * @date 2018/01/03 08:48
 */
@Data
public class RolePrivilegeSimpleDTO {

    @ApiModelProperty("父级Key")
    private String parentKey;
    /**
     * 功能名称
     */
    @ApiModelProperty("名称")
    private String name;

    private Integer type;

    @ApiModelProperty("key")
    private String key;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("排序")
    private Integer sort;

    @ApiModelProperty("子级列表")
    private List<RolePrivilegeSimpleDTO> children;


}
