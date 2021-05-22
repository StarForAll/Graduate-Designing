package cn.edu.ncu.common.heartbeat;

import lombok.Builder;
import lombok.Data;

/**
 * @Author: XiongZhiCong
 * @Description:
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Data
@Builder
public class HeartBeatConfig {

    /**
     * 延迟执行时间
     */
    private Long delayHandlerTime;

    /**
     * 间隔执行时间
     */
    private Long intervalTime;
}
