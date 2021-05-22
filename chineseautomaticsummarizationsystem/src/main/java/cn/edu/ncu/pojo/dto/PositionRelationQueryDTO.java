package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位关系DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PositionRelationQueryDTO {

    @ApiModelProperty("岗位ID")
    private Long positionId;

    @ApiModelProperty("员工ID")
    private Long employeeId;

}
