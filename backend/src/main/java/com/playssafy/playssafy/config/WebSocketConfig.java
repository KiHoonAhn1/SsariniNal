package com.playssafy.playssafy.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer { // WebSocketMessageBroker 설정 인터페이스 추가
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/playssafy") // Client가 Websocket Handshake Connection을 생성할 경로 설정(초기 연결 수립)
                .setAllowedOriginPatterns("*") // CORS 설정
                .withSockJS(); // SockJS 활성화
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/pub"); // Client가 Message를 퍼블리싱할 주소(메세지를 보내는 곳)
    }
}
