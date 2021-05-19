package cn.edu.ncu.common.util.dao;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @Author: XiongZhiCong
 * @Description: 雪花算法 ID 生成
 * @Date: Created in 14:54 2021/4/8
 * @Modified By:
 */
@Slf4j
@Component
public class IdGeneratorUtil {

    @Value("${snowflake.workerId: 0}")
    private long workerId;
    @Value("${snowflake.dataCenterId: 1}")
    private long dataCenterId;

    private Snowflake snowflake;

    @PostConstruct
    public void init() {
        try {
            log.info("当前机器的 workerId: {}", workerId);
            snowflake = IdUtil.createSnowflake(workerId, dataCenterId);
        } catch (Exception e) {
            log.error("当前机器的 workerId 获取失败: {}", e.getMessage());
            workerId = NetUtil.getLocalhostStr().hashCode();
            log.error("当前机器 workId:{}", workerId);
        }
    }

    public synchronized long snowflakeId() {
        return snowflake.nextId();
    }

}
