package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.QuartzConst;
import cn.edu.ncu.common.constant.TaskResultEnum;
import cn.edu.ncu.common.util.task.QuartzUtil;
import cn.edu.ncu.common.util.web.IpUtil;
import cn.edu.ncu.dao.entity.QuartzTaskEntity;
import cn.edu.ncu.dao.entity.QuartzTaskLog;
import cn.edu.ncu.service.ApplicationContextHolder;
import cn.edu.ncu.service.ITask;
import cn.edu.ncu.service.QuartzTaskLogService;
import cn.edu.ncu.service.QuartzTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
public class QuartzTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        Object params = context.getMergedJobDataMap().get(QuartzConst.QUARTZ_PARAMS_KEY);
        JobKey jobKey = jobDetail.getKey();

        Long taskId = QuartzUtil.getTaskIdByJobKey(jobKey);
        QuartzTaskService quartzTaskService = (QuartzTaskService) ApplicationContextHolder.getBean("quartzTaskServiceImpl");
        QuartzTaskEntity quartzTaskEntity = quartzTaskService.getByTaskId(taskId);

        QuartzTaskLogService quartzTaskLogService = (QuartzTaskLogService) ApplicationContextHolder.getBean("quartzTaskLogServiceImpl");

        QuartzTaskLog taskLogEntity = new QuartzTaskLog();
        taskLogEntity.setTaskId(taskId);
        taskLogEntity.setIpAddress(IpUtil.getLocalHostIP());
        try {
            taskLogEntity.setTaskName(quartzTaskEntity.getTaskName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String paramsStr = null;
        if (params != null) {
            paramsStr = params.toString();
            taskLogEntity.setTaskParams(paramsStr);
        }
        taskLogEntity.setUpdateTime(new Date());
        taskLogEntity.setCreateTime(new Date());
        //任务开始时间
        long startTime = System.currentTimeMillis();
        try {
            ITask taskClass = (ITask) ApplicationContextHolder.getBean(quartzTaskEntity.getTaskBean());
            taskClass.execute(paramsStr);
            taskLogEntity.setProcessStatus(TaskResultEnum.SUCCESS.getStatus());
        } catch (Exception e) {
            log.error("", e);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw, true);
            e.printStackTrace(pw);
            pw.flush();
            sw.flush();
            taskLogEntity.setProcessStatus(TaskResultEnum.FAIL.getStatus());
            taskLogEntity.setProcessLog(sw.toString());
        } finally {
            long times = System.currentTimeMillis() - startTime;
            taskLogEntity.setProcessDuration(times);
            quartzTaskLogService.save(taskLogEntity);
        }

    }

}
