package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: XiongZhiCong
 * @Description: 修改密码所需参数DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class EmployeeUpdatePwdDTO {

    @ApiModelProperty("新密码")
    @NotNull
    private String pwd;

    @ApiModelProperty("原密码")
    @NotNull
    private String oldPwd;

}
