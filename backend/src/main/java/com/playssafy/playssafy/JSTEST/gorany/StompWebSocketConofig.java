//package com.playssafy.playssafy.JSTEST.gorany;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
////@EnableWebSocketMessageBroker
////@Configuration
//public class StompWebSocketConofig implements WebSocketMessageBrokerConfigurer {
//    @Override
//    // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//        registry.enableSimpleBroker("/sub");
//        //메세지브로커로 라우팅 되게, 연결된 클라이언트에게 브로케스트(송신)함
//        registry.setApplicationDestinationPrefixes("/pub");
//        //컨트롤러의 메세지매핑 어노테이션 된 메세지 핸들러 메소드로 라우팅
//
//    }
//
//    @Override
//    // connection을 맺을때 CORS 허용합니다.
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/stomp/chat")
//                .setAllowedOriginPatterns("*")
//                .withSockJS();
//
//    }
//}
