package cn.edu.ncu.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 权限请求路径VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PrivilegeRequestUrlVO {

    @ApiModelProperty("注释说明")
    private String comment;

    @ApiModelProperty("controller.method")
    private String name;

    @ApiModelProperty("url")
    private String url;
}
