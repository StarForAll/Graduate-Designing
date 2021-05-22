package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.util.dao.IdGeneratorUtil;
import cn.edu.ncu.dao.entity.QuartzTaskLog;
import cn.edu.ncu.dao.mapper.QuartzTaskLogMapper;
import cn.edu.ncu.service.QuartzTaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 定时任务记录业务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Service
public class QuartzTaskLogServiceImpl implements QuartzTaskLogService {

    @Resource
    private QuartzTaskLogMapper quartzTaskLogMapper;
    @Autowired
    private IdGeneratorUtil idGeneratorUtil;

    @Override
    public void save(QuartzTaskLog logEntity){
        if(logEntity.getId()==null){
            logEntity.setId(idGeneratorUtil.snowflakeId());
        }
        Date date=new Date(System.currentTimeMillis());
        if(logEntity.getUpdateTime()==null){
            logEntity.setUpdateTime(date);
        }
        if(logEntity.getCreateTime()==null){
            logEntity.setCreateTime(date);
        }
        quartzTaskLogMapper.insert(logEntity);
    }
}
