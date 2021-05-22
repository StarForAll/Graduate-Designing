package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 员工更新DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class EmployeeUpdateDTO extends EmployeeBaseDTO {

    @ApiModelProperty("员工id")
    @NotNull(message = "员工id不能为空")
    private Long id;

    @ApiModelProperty("密码")
    private String loginPwd;

    @ApiModelProperty("岗位ID 集合")
    private List<Long> positionIdList;

}
