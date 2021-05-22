package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
/**
 * @Author: XiongZhiCong
 * @Description: 系统设置更新DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class SystemConfigUpdateDTO extends SystemConfigAddDTO{

    @ApiModelProperty("id")
    @NotNull(message = "id不能为空")
    private Long id;
}
