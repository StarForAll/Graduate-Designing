package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.util.basic.ThreadFactory;
import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.OrderOperateLog;
import cn.edu.ncu.dao.entity.UserLoginLog;
import cn.edu.ncu.dao.entity.UserOperateLog;
import cn.edu.ncu.dao.mapper.OrderOperateLogMapper;
import cn.edu.ncu.dao.mapper.UserLoginLogMapper;
import cn.edu.ncu.dao.mapper.UserOperateLogMapper;
import cn.edu.ncu.service.LogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/4/4 0004 下午 16:19
 * @since JDK1.8
 */
@Slf4j
@Service
public class LogServiceImpl implements LogService {

    private ThreadPoolExecutor threadPoolExecutor;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;
    @Resource
    private UserLoginLogMapper userLoginLogMapper;

    @Resource
    private OrderOperateLogMapper orderOperateLogMapper;

    @Resource
    private UserOperateLogMapper userOperateLogMapper;

    @PostConstruct
    void init() {
        if (threadPoolExecutor == null) {
            threadPoolExecutor = new ThreadPoolExecutor(1, 1, 10L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(2000), ThreadFactory.create("LogAspect"));
        }
    }

    @PreDestroy
    void destroy() {
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdown();
            threadPoolExecutor = null;
        }
    }

    @Override
    public void addLog(Object object) {
        try {
            Date date=new Date(System.currentTimeMillis());
            if (object instanceof UserLoginLog) {
                UserLoginLog userLoginLog=(UserLoginLog) object;
                if(userLoginLog.getId()==null){
                    userLoginLog.setId(idGeneratorUtil.snowflakeId());
                }
                if(userLoginLog.getUpdateTime()==null){
                    userLoginLog.setUpdateTime(date);
                }
                if(userLoginLog.getCreateTime()==null){
                    userLoginLog.setCreateTime(date);
                }
                threadPoolExecutor.execute(() -> userLoginLogMapper.insert(userLoginLog));
            }
            if (object instanceof OrderOperateLog) {
                OrderOperateLog orderOperateLog=(OrderOperateLog) object;
                if(orderOperateLog.getId()==null){
                    orderOperateLog.setId(idGeneratorUtil.snowflakeId());
                }
                if(orderOperateLog.getUpdateTime()==null){
                    orderOperateLog.setUpdateTime(date);
                }
                if(orderOperateLog.getCreateTime()==null){
                    orderOperateLog.setCreateTime(date);
                }
                threadPoolExecutor.execute(() -> orderOperateLogMapper.insert(orderOperateLog));
            }
            if (object instanceof UserOperateLog) {
                UserOperateLog userOperateLog=(UserOperateLog) object;
                if(userOperateLog.getId()==null){
                    userOperateLog.setId(idGeneratorUtil.snowflakeId());
                }
                if(userOperateLog.getUpdateTime()==null){
                    userOperateLog.setUpdateTime(date);
                }
                if(userOperateLog.getCreateTime()==null){
                    userOperateLog.setCreateTime(date);
                }
                threadPoolExecutor.execute(() -> userOperateLogMapper.insert(userOperateLog));
            }
        } catch (Throwable e) {
            log.error("userLogAfterAdvice:{}", e);
        }
    }
}
