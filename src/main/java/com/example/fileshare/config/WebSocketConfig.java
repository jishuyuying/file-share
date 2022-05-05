package com.example.fileshare.config;

import com.example.fileshare.websocket.HttpAuthHandler;
import com.example.fileshare.websocket.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author jmz jianminzhao@foxmail.com
 * @since 5/5/2022 下午 3:05
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Autowired
    private HttpAuthHandler httpAuthHandler;
    @Autowired
    private MyInterceptor myInterceptor;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry
                .addHandler(httpAuthHandler, "websocket")
                .addInterceptors(myInterceptor)
                .setAllowedOrigins("*");
    }

}