package cn.edu.ncu.common.core.pojo.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;
/**
 * @Author: XiongZhiCong
 * @Description: 页面对象参数
 * @Date: Created in 14:16 2021/4/8
 * @Modified By:
 */
@Data
public class PageParamDTO {

    @NotNull(message = "分页参数不能为空")
    @ApiModelProperty(value = "页码(不能为空)", example = "1")
    protected Integer pageNum;

    @NotNull(message = "每页数量不能为空")
    @ApiModelProperty(value = "每页数量(不能为空)", example = "10")
    @Max(value = 500, message = "每页最大为500")
    protected Integer pageSize;

    @ApiModelProperty("是否查询总条数")
    protected Boolean searchCount;

    @ApiModelProperty("排序")
    protected List<OrderItemDTO> orders;
}