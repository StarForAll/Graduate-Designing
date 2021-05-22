package cn.edu.ncu.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description: 数据范围选择类型VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Builder
public class DataScopeViewTypeVO {

    @ApiModelProperty("可见范围")
    private Integer viewType;
    @ApiModelProperty("可见范围名称")
    private String viewTypeName;

    @ApiModelProperty("级别,用于表示范围大小")
    private Integer viewTypeLevel;
}
