package cn.edu.ncu.pojo.vo;
import lombok.Data;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: reload结果VO
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
public class ReloadResultVO {

    /**
     * 加载项标签
     */
    private String tag;

    /**
     * 参数
     */
    private String args;

    /**
     *  状态标识
     */
    private String identification;

    /**
     * 运行结果
     */
    private Boolean result;

    /**
     * 异常
     */
    private String exception;

    /**
     * 创建时间
     */
    private Date createTime;

}
