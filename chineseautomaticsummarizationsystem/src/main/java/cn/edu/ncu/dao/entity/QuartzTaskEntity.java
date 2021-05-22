package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;


/**
 * @Author: XiongZhiCong
 * @Description: 定时任务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="quartz_task")
public class QuartzTaskEntity extends BaseEntity {
    /**
     * 任务名称参数
     */
    private String taskName;
    /**
     * 任务类
     */
    private String taskBean;

    /**
     * 任务参数
     */
    private String taskParams;

    /**
     * cron
     */
    private String taskCron;

    /**
     * 任务状态
     */
    private Integer taskStatus;

    /**
     * 备注
     */
    private String remark;

}
