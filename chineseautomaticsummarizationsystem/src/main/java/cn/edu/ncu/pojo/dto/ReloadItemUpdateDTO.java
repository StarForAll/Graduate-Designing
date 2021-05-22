package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author: XiongZhiCong
 * @Description: reload项更新DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class ReloadItemUpdateDTO {

    @ApiModelProperty("标签")
    @NotBlank(message = "标签不能为空")
    private String tag;

    @ApiModelProperty("状态标识")
    @NotBlank(message = "状态标识不能为空")
    private String identification;

    @ApiModelProperty("reload时传入的参数，可以为空")
    private String args;
}
