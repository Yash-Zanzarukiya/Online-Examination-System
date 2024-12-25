package com.yashpz.examination_system.examination_system.config;

import com.yashpz.examination_system.examination_system.socket.SessionTokenHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.yashpz.examination_system.examination_system.socket.ExamWebSocketHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {

    private final ExamWebSocketHandler examWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(examWebSocketHandler, "/ws/exam")
                .addInterceptors(new SessionTokenHandshakeInterceptor())
                .setAllowedOrigins("http://localhost:5173", "http://localhost:8080");
    }
}
