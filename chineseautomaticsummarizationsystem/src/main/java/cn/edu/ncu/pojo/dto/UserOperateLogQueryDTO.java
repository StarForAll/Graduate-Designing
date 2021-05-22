package cn.edu.ncu.pojo.dto;

import cn.edu.ncu.common.core.pojo.dto.PageParamDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 用户操作记录查询DTO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class UserOperateLogQueryDTO extends PageParamDTO {


    @ApiModelProperty("开始日期")
    private String startDate;

    @ApiModelProperty("结束日期")
    private String endDate;


    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("请求结果 0失败 1成功")
    private Integer resultFlag;

}
