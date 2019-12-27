package com.nnxy.jgz.oasystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * webSocket配置类
 * @author JGZ
 * CreateTime 2019/12/27 9:34
 * Email 1945282561@qq.com
 */
@Component
public class WebSocketConfig {

    /**
     * 实例化服务对象
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return  new ServerEndpointExporter();
    }
}
