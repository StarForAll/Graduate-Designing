package cn.edu.ncu.common.config;

import cn.edu.ncu.common.reload.ReloadManager;
import cn.edu.ncu.service.ReloadThreadLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: XiongZhiCong
 * @Description: reload设置
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Slf4j
@Configuration
public class ReloadConfig {

    @Value("${reload.thread-count}")
    private Integer threadCount;

    @Bean
    public ReloadManager initReloadManager() {
        /**
         * 创建 Reload Manager 调度器
         */
        ReloadManager reloadManager = new ReloadManager(new ReloadThreadLogger() {
            @Override
            public void error(String string) {
                log.error(string);
            }

            @Override
            public void error(String string, Throwable e) {
                log.error(string, e);
            }
        }, threadCount);
        return reloadManager;
    }
}
