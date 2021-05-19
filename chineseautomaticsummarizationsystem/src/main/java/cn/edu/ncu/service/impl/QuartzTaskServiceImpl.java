package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.constant.QuartzConst;
import cn.edu.ncu.common.constant.ResponseCodeConst;
import cn.edu.ncu.common.constant.TaskStatusEnum;
import cn.edu.ncu.common.util.bean.BeanUtil;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.common.util.task.QuartzUtil;
import cn.edu.ncu.dao.mapper.QuartzTaskLogMapper;
import cn.edu.ncu.dao.mapper.QuartzTaskMapper;
import cn.edu.ncu.service.ApplicationContextHolder;
import cn.edu.ncu.service.QuartzTaskService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.dao.entity.QuartzTaskEntity;
import cn.edu.ncu.pojo.dto.QuartzLogQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzTaskDTO;
import cn.edu.ncu.pojo.vo.QuartzTaskLogVO;
import cn.edu.ncu.pojo.vo.QuartzTaskVO;
/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/4/13 0013 下午 14:50
 * @since JDK1.8
 */
@Slf4j
@Service
public class QuartzTaskServiceImpl implements QuartzTaskService {
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private QuartzTaskMapper quartzTaskMapper;

    @Resource
    private QuartzTaskLogMapper quartzTaskLogMapper;

    @Autowired
    private Scheduler scheduler;

    /**
     * 查询列表
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<QuartzTaskVO>> query(QuartzQueryDTO queryDTO) {
        PageResultDTO<QuartzTaskVO> pageResultDTO = new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=quartzTaskMapper.queryListCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<QuartzTaskVO> taskList = quartzTaskMapper.queryList(queryDTO);
        pageResultDTO.setList(taskList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 查询运行日志
     *
     * @param queryDTO
     * @return
     */
    @Override
    public ResponseDTO<PageResultDTO<QuartzTaskLogVO>> queryLog(QuartzLogQueryDTO queryDTO) {
        PageResultDTO<QuartzTaskLogVO> pageResultDTO=new PageResultDTO<>();
        int total=0;
        Boolean searchCount = queryDTO.getSearchCount();
        if(searchCount ==null||searchCount){
            total=quartzTaskLogMapper.queryListCount(queryDTO);
        }
        long pages=total/queryDTO.getPageSize()+1;
        pageResultDTO.setPageNum(Long.valueOf(queryDTO.getPageNum()));
        pageResultDTO.setPages(pages);
        pageResultDTO.setTotal((long) total);
        pageResultDTO.setPageSize(Long.valueOf(queryDTO.getPageSize()));

        List<QuartzTaskLogVO> taskList = quartzTaskLogMapper.queryList(queryDTO);
        pageResultDTO.setList(taskList);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 保存或更新
     *
     * @param quartzTaskDTO
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDTO<String> saveOrUpdateTask(QuartzTaskDTO quartzTaskDTO) throws Exception {
        ResponseDTO baseValid = this.baseValid(quartzTaskDTO);
        if (!baseValid.isSuccess()) {
            return baseValid;
        }
        Long taskId = quartzTaskDTO.getId();
        if (taskId == null) {
            return this.saveTask(quartzTaskDTO);
        } else {
            return this.updateTask(quartzTaskDTO);
        }
    }

    private ResponseDTO<String> baseValid(QuartzTaskDTO quartzTaskDTO) {
        Object taskBean = null;
        try {
            taskBean = ApplicationContextHolder.getBean(quartzTaskDTO.getTaskBean());
        } catch (Exception e) {
            log.error("taskBean 不存在{}", e);
        }
        if (taskBean == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "taskBean 不存在");
        }
        if (!CronExpression.isValidExpression(quartzTaskDTO.getTaskCron())) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "请传入正确的正则表达式");
        }
        return ResponseDTO.succ();
    }

    private ResponseDTO<String> saveTask(QuartzTaskDTO quartzTaskDTO) throws Exception {
        QuartzTaskEntity taskEntity = BeanUtil.copy(quartzTaskDTO, QuartzTaskEntity.class);
        if(taskEntity.getId()==null){
            taskEntity.setId(idGeneratorUtil.snowflakeId());
        }
        taskEntity.setTaskStatus(TaskStatusEnum.NORMAL.getStatus());
        Date date=new Date(System.currentTimeMillis());
        taskEntity.setUpdateTime(date);
        taskEntity.setCreateTime(date);
        quartzTaskMapper.insert(taskEntity);
        this.createQuartzTask(scheduler, taskEntity);
        return ResponseDTO.succ();
    }

    private ResponseDTO<String> updateTask(QuartzTaskDTO quartzTaskDTO) throws Exception {
        QuartzTaskEntity updateEntity = quartzTaskMapper.selectByPrimaryKey(quartzTaskDTO.getId());
        if (updateEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        QuartzTaskEntity taskEntity = BeanUtil.copy(quartzTaskDTO, QuartzTaskEntity.class);
        //任务状态不能更新
        taskEntity.setTaskStatus(updateEntity.getTaskStatus());
        taskEntity.setUpdateTime(new Date());
        quartzTaskMapper.update(taskEntity);
        if(this.checkExist(taskEntity.getId())){
            this.updateQuartzTask(scheduler, taskEntity);
        }else{
            this.createQuartzTask(scheduler,taskEntity);
        }

        return ResponseDTO.succ();
    }

    /**
     * 立即运行
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDTO<String> runTask(Long taskId) throws Exception {
        QuartzTaskEntity quartzTaskEntity = quartzTaskMapper.selectByPrimaryKey(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        this.runQuartzTask(scheduler, quartzTaskEntity);
        return ResponseDTO.succ();
    }

    /**
     * 暂停运行
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDTO<String> pauseTask(Long taskId) throws Exception {
        QuartzTaskEntity quartzTaskEntity = quartzTaskMapper.selectByPrimaryKey(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        quartzTaskEntity.setTaskStatus(TaskStatusEnum.PAUSE.getStatus());
        quartzTaskMapper.update(quartzTaskEntity);
        this.pauseQuartzTask(scheduler, quartzTaskEntity);
        return ResponseDTO.succ();
    }

    /**
     * 恢复任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDTO<String> resumeTask(Long taskId) throws Exception {
        QuartzTaskEntity quartzTaskEntity = quartzTaskMapper.selectByPrimaryKey(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        quartzTaskEntity.setTaskStatus(TaskStatusEnum.NORMAL.getStatus());
        quartzTaskMapper.update(quartzTaskEntity);
        this.resumeQuartzTask(scheduler, quartzTaskEntity);
        return ResponseDTO.succ();
    }

    /**
     * 删除任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Override
    public ResponseDTO<String> deleteTask(Long taskId) throws Exception {
        QuartzTaskEntity quartzTaskEntity = quartzTaskMapper.selectByPrimaryKey(taskId);
        if (quartzTaskEntity == null) {
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM, "task不存在");
        }
        quartzTaskMapper.deleteByPrimaryKey(taskId);
        this.deleteQuartzTask(scheduler, taskId);
        return ResponseDTO.succ();
    }

    /**
     * 通过任务Id 获取任务实体
     *
     * @param taskId
     * @return
     */
    @Override
    public QuartzTaskEntity getByTaskId(Long taskId) {
        return quartzTaskMapper.selectByPrimaryKey(taskId);
    }

    /**
     * 创建任务
     *
     * @param scheduler
     * @param taskEntity
     * @throws Exception
     */
    @Override
    public void createQuartzTask(Scheduler scheduler, QuartzTaskEntity taskEntity) throws Exception {
        JobKey jobKey = QuartzUtil.getJobKey(taskEntity.getId());
        JobDetail jobDetail = JobBuilder.newJob(QuartzTask.class).withIdentity(jobKey).build();

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskEntity.getTaskCron()).withMisfireHandlingInstructionDoNothing();

        TriggerKey triggerKey = QuartzUtil.getTriggerKey(Long.valueOf(taskEntity.getId()));
        CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        jobDetail.getJobDataMap().put(QuartzConst.QUARTZ_PARAMS_KEY, taskEntity.getTaskParams());
        scheduler.scheduleJob(jobDetail, trigger);
        //如果任务是暂停状态，则暂停任务
        if (TaskStatusEnum.PAUSE.getStatus().equals(taskEntity.getTaskStatus())) {
            this.pauseQuartzTask(scheduler, taskEntity);
        }
    }

    /**
     * 更新任务
     *
     * @param scheduler
     * @param taskEntity
     * @throws Exception
     */
    private void updateQuartzTask(Scheduler scheduler, QuartzTaskEntity taskEntity) throws Exception {
        TriggerKey triggerKey = QuartzUtil.getTriggerKey(Long.valueOf(taskEntity.getId()));

        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(taskEntity.getTaskCron()).withMisfireHandlingInstructionDoNothing();

        CronTrigger trigger = this.getCronTrigger(scheduler, Long.valueOf(taskEntity.getId()));

        trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

        trigger.getJobDataMap().put(QuartzConst.QUARTZ_PARAMS_KEY, taskEntity.getTaskParams());

        scheduler.rescheduleJob(triggerKey, trigger);
        //如果更新之前任务是暂停状态，此时再次暂停任务
        if (TaskStatusEnum.PAUSE.getStatus().equals(taskEntity.getTaskStatus())) {
            this.pauseQuartzTask(scheduler, taskEntity);
        }
    }

    private CronTrigger getCronTrigger(Scheduler scheduler, Long taskId) throws Exception {
        TriggerKey triggerKey = QuartzUtil.getTriggerKey(taskId);
        return (CronTrigger) scheduler.getTrigger(triggerKey);
    }

    /**
     * 立即运行
     *
     * @param scheduler
     * @param taskEntity
     * @throws Exception
     */
    private void runQuartzTask(Scheduler scheduler, QuartzTaskEntity taskEntity) throws Exception {
        JobDataMap dataMap = new JobDataMap();
        dataMap.put(QuartzConst.QUARTZ_PARAMS_KEY, taskEntity.getTaskParams());
        JobKey jobKey = QuartzUtil.getJobKey(taskEntity.getId());
        if(!scheduler.checkExists(jobKey)){
            this.createQuartzTask(scheduler,taskEntity);
            scheduler.triggerJob(jobKey, dataMap);
            return;
        }
        scheduler.triggerJob(jobKey, dataMap);
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param quartzTaskEntity
     * @throws Exception
     */
    private void pauseQuartzTask(Scheduler scheduler, QuartzTaskEntity quartzTaskEntity) throws Exception {
        JobKey jobKey = QuartzUtil.getJobKey(quartzTaskEntity.getId());
        if(!scheduler.checkExists(jobKey)){
            this.createQuartzTask(scheduler,quartzTaskEntity);
            scheduler.pauseJob(jobKey);
            return;
        }
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param quartzTaskEntity
     * @throws Exception
     */
    private void resumeQuartzTask(Scheduler scheduler, QuartzTaskEntity quartzTaskEntity) throws Exception {
        JobKey jobKey = QuartzUtil.getJobKey(quartzTaskEntity.getId());
        if(!scheduler.checkExists(jobKey)){
            this.createQuartzTask(scheduler,quartzTaskEntity);
            return;
        }
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除任务
     *
     * @param scheduler
     * @param taskId
     * @throws Exception
     */
    private void deleteQuartzTask(Scheduler scheduler, Long taskId) throws Exception {
        JobKey jobKey = QuartzUtil.getJobKey(taskId);
        if(!scheduler.checkExists(jobKey)){
            return;
        }
        scheduler.deleteJob(jobKey);
    }


    private Boolean checkExist(Long taskId) throws Exception{
        JobKey jobKey = QuartzUtil.getJobKey(taskId);
        return scheduler.checkExists(jobKey);
    }
}
