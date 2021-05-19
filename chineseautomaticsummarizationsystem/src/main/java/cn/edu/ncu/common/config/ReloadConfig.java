package cn.edu.ncu.common.config;

import cn.edu.ncu.common.reload.ReloadManager;
import cn.edu.ncu.service.ReloadThreadLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [  ]
 *
 * @author yandanyang
 * @version 1.0
 * @company 1024lab.net
 * @copyright (c) 2018 1024lab.netInc. All rights reserved.
 * @date 2019/3/28 0028 下午 20:15
 * @since JDK1.8
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
