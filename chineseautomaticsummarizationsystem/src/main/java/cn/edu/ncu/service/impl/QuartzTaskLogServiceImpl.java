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
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/4/13 0013 下午 14:50
 * @since JDK1.8
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
