package cn.edu.ncu.service.impl;

import cn.edu.ncu.common.util.basic.DateUtil;
import cn.edu.ncu.service.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: XiongZhiCong
 * @Description: 前端定时任务
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Component("exampleTask")
public class Example implements ITask {

    @Override
    public void execute(String paramJson) throws Exception {
        log.warn("{}-执行定时任务,任务参数:{}", DateUtil.formatYMDHMS(new Date()),paramJson );
    }
}
