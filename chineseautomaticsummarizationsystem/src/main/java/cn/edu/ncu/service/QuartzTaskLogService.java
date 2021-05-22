package cn.edu.ncu.service;


import cn.edu.ncu.dao.entity.QuartzTaskLog;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务日志
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
public interface QuartzTaskLogService {



     void save(QuartzTaskLog logEntity);
}
