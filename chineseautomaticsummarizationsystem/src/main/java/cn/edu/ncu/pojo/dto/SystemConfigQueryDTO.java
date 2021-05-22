package cn.edu.ncu.pojo.dto;


import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 系统设置查询DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class SystemConfigQueryDTO extends PageParamDTO {

    @ApiModelProperty("参数KEY")
    private String key;

    @ApiModelProperty("参数类别")
    private String configGroup;


}
