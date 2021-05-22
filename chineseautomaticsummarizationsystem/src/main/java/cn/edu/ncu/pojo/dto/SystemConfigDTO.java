package cn.edu.ncu.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 系统设置DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class SystemConfigDTO {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("参数key")
    private String configKey;

    @ApiModelProperty("参数的值")
    private String configValue;

    @ApiModelProperty("参数名称")
    private String configName;

    @ApiModelProperty("参数类别")
    private String configGroup;

    @ApiModelProperty("是否使用0 是 1否")
    private Integer isUsing;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("上次修改时间")
    private Date updateTime;


}
