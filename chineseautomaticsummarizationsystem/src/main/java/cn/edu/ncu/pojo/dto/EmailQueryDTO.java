package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 邮件查询DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class EmailQueryDTO extends PageParamDTO {


    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;


    @ApiModelProperty("标题")
    private String title;


    @ApiModelProperty("发送状态 0未发送 1已发送")
    private Integer sendStatus;

}
