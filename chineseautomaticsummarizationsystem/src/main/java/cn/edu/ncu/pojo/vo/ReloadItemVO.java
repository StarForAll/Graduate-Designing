package cn.edu.ncu.pojo.vo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: reload项VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class ReloadItemVO {

    /**
     * 加载项标签
     */
    @ApiModelProperty("加载项标签")
    private String tag;

    /**
     * 参数
     */
    @ApiModelProperty("参数")
    private String args;

    /**
     * 状态标识
     */
    @ApiModelProperty("状态标识")
    private String identification;

    /**
     * 更新时间
     */
    @ApiModelProperty("最后更新时间")
    private Date updateTime;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private Date createTime;

}
