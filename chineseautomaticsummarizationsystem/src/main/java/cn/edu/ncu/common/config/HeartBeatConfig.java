package cn.edu.ncu.common.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: XiongZhiCong
 * @Description: 心跳服务设置
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Configuration
public class HeartBeatConfig {

    /**
     * 延迟执行时间
     */
    @Value("${heart-beat.delayHandlerTime}")
    private Long delayHandlerTime;

    /**
     * 间隔执行时间
     */
    @Value("${heart-beat.intervalTime}")
    private Long intervalTime;
}
