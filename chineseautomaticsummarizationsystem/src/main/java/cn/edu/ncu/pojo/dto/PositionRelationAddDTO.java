package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位关系DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PositionRelationAddDTO {
    @ApiModelProperty("岗位ID")
    @NotNull(message = "岗位ID 不能为空")
    private List<Long> positionIdList;

    @ApiModelProperty("员工ID")
    @NotNull(message = "员工ID 不能为空")
    private Long employeeId;

    public PositionRelationAddDTO() {
    }

    public PositionRelationAddDTO(List<Long> positionIdList, Long employeeId) {
        this.positionIdList = positionIdList;
        this.employeeId = employeeId;
    }
}
