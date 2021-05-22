package cn.edu.ncu.service;

import cn.edu.ncu.common.core.pojo.dto.PageResultDTO;
import cn.edu.ncu.common.core.pojo.dto.ResponseDTO;
import cn.edu.ncu.dao.entity.QuartzTaskEntity;
import cn.edu.ncu.pojo.dto.QuartzLogQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzQueryDTO;
import cn.edu.ncu.pojo.dto.QuartzTaskDTO;
import cn.edu.ncu.pojo.vo.QuartzTaskLogVO;
import cn.edu.ncu.pojo.vo.QuartzTaskVO;
import org.quartz.*;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: XiongZhiCong
 * @Description: 定时任务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface QuartzTaskService {

    /**
     * 查询列表
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<QuartzTaskVO>> query(QuartzQueryDTO queryDTO) ;

    /**
     * 查询运行日志
     *
     * @param queryDTO
     * @return
     */
     ResponseDTO<PageResultDTO<QuartzTaskLogVO>> queryLog(QuartzLogQueryDTO queryDTO) ;

    /**
     * 保存或更新
     *
     * @param quartzTaskDTO
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
     ResponseDTO<String> saveOrUpdateTask(QuartzTaskDTO quartzTaskDTO) throws Exception ;



    /**
     * 立即运行
     *
     * @param taskId
     * @return
     * @throws Exception
     */
     ResponseDTO<String> runTask(Long taskId) throws Exception ;

    /**
     * 暂停运行
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
     ResponseDTO<String> pauseTask(Long taskId) throws Exception ;

    /**
     * 恢复任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
    @Transactional(rollbackFor = Throwable.class)
     ResponseDTO<String> resumeTask(Long taskId) throws Exception ;

    /**
     * 删除任务
     *
     * @param taskId
     * @return
     * @throws Exception
     */
     ResponseDTO<String> deleteTask(Long taskId) throws Exception ;

    /**
     * 通过任务Id 获取任务实体
     *
     * @param taskId
     * @return
     */
     QuartzTaskEntity getByTaskId(Long taskId);

    /**
     * 创建任务
     *
     * @param scheduler
     * @param taskEntity
     * @throws Exception
     */
     void createQuartzTask(Scheduler scheduler, QuartzTaskEntity taskEntity) throws Exception;

}
