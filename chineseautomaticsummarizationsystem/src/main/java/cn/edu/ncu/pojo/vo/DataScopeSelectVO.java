package cn.edu.ncu.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围选择VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class DataScopeSelectVO {

    @ApiModelProperty("数据范围id")
    private Integer dataScopeType;

    @ApiModelProperty("可见范围")
    private Integer viewType;
}
