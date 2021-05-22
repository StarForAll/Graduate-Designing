package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 员工状态批量更新
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class EmployeeBatchUpdateStatusDTO {

    @ApiModelProperty("员工ids")
    @NotNull(message = "员工ids不能为空")
    private List<Long> employeeIds;

    @ApiModelProperty("状态")
    @NotNull(message = "状态不能为空")
    private Integer status;

}
