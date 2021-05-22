package cn.edu.ncu.common.config.spring.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @Author: XiongZhiCong
 * @Description: [ WebSocketConfig ]
 * @Date: Created in 10:31 2021/4/21
 * @Modified By:
 */
@Configuration
public class SmartWebSocketConfig {

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
