//package com.playssafy.playssafy.JSTEST;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
////핸들러 활성화
//@Configuration//bean 설정 의미ㅣ
////@EnableWebSocketMessageBroker//웹소켓 서버 활서화
//@EnableWebSocket//웹소켓 할성화
//@RequiredArgsConstructor
//public class WebSocketConfigJs implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
//    public void configureMessageBroker(MessageBrokerRegistry config) {
////        config.enableSimpleBroker("/send");
//        config.enableSimpleBroker("/topic");
//        //cre로 시작하는 것을 메세지브로커로 라우팅 되게, 연결된 클라이언트에게 브로케스트(송신)함
//        config.setApplicationDestinationPrefixes("/num");
//        //num 시작 메세지를 메세지 핸들러 메소드로 라우팅
//
//    }
//
//    @Override
//    // connection을 맺을때 CORS 허용합니다.
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
////        registry.addEndpoint("/").setAllowedOrigins("**").withSockJS(); 에러 발생
//        registry.addEndpoint("/").setAllowedOriginPatterns("*").withSockJS();
//
//    }
//}
