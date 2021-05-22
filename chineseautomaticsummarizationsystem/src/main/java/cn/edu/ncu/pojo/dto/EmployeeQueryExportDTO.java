package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 员工查询DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class EmployeeQueryExportDTO {

    @ApiModelProperty(hidden = true)
    private String phone;

    @ApiModelProperty("姓名")
    private String actualName;

    @ApiModelProperty(hidden = true)
    private String keyword;

    @ApiModelProperty(hidden = true)
    private Long departmentId;

    @ApiModelProperty(hidden = true)
    private Integer isLeave;

    @ApiModelProperty(hidden = true)
    private Integer isDisabled;

    /**
     * 删除状态 0否 1是
     */
    @ApiModelProperty(value = "删除状态 0否 1是 不需要传", hidden = true)
    private Integer isDelete;

    @ApiModelProperty(value = "员工ID集合", hidden = true)
    private List<Long> employeeIds;

}
