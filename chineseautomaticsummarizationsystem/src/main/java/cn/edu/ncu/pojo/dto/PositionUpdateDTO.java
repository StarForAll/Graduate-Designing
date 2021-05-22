package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位更新DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PositionUpdateDTO extends PositionAddDTO {

    @ApiModelProperty("主键")
    private Long id;
}
