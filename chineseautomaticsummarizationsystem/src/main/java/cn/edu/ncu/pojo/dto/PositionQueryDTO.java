package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 岗位查询DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class PositionQueryDTO extends PageParamDTO {

    @ApiModelProperty("岗位名称")
    private String positionName;

}
