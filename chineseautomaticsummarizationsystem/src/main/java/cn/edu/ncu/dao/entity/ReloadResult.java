package cn.edu.ncu.dao.entity;
import lombok.Data;

import javax.persistence.Table;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: t_reload_result 数据表 实体类
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="reload_result")
public class ReloadResult {

    /**
     * 加载项标签
     */
    private String tag;

    /**
     * 运行标识
     */
    private String identification;

    /**
     * 参数
     */
    private String args;

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


    public ReloadResult() {
    }

    public ReloadResult(String tag, String args, boolean result, String exception) {
        this.tag = tag;
        this.args = args;
        this.result = result;
        this.exception = exception;
    }

    public ReloadResult(String tag, String args, String identification, boolean result, String exception) {
        this.tag = tag;
        this.args = args;
        this.identification = identification;
        this.result = result;
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "ReloadResult{" +
                "tag='" + tag + '\'' +
                ", identification='" + identification + '\'' +
                ", args='" + args + '\'' +
                ", result=" + result +
                ", exception='" + exception + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
