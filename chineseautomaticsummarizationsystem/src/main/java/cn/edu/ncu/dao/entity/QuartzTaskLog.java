package cn.edu.ncu.dao.entity;

import cn.edu.ncu.common.core.pojo.entity.BaseEntity;
import lombok.Data;

import javax.persistence.Table;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务日志
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Table(name="quartz_task_log")
public class QuartzTaskLog extends BaseEntity {
    /**
     * 任务名称参数
     */
    private Long taskId;
    /**
     * 任务名称
     */
    private String taskName;
    /**
     * 任务参数
     */
    private String taskParams;
    /**
     * 任务处理状态
     */
    private Integer processStatus;

    /**
     * 任务时长ms
     */
    private Long processDuration;

    /**
     * 处理日志
     */
    private String processLog;


    private String ipAddress;

}
