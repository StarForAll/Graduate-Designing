package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 登录权限DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class LoginPrivilegeDTO {

    @ApiModelProperty("权限key")
    private String key;

    private Integer type;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("父级key")
    private String parentKey;

}
