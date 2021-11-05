//package com.playssafy.playssafy.JSTEST.gorany;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.*;
//
////핸들러 활성화
////@Configuration//bean 설정 의미ㅣ
////@EnableWebSocket//웹소켓 할성화
////@RequiredArgsConstructor
////@EnableWebSocketMessageBroker
//public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
//    ////고라니/////////
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//            registry.addEndpoint("/stomp/chat")
//                    .setAllowedOriginPatterns("*")
//                    .withSockJS();
//
//    }
//        @Override
//    // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
//    public void configureMessageBroker(MessageBrokerRegistry registry) {
//            registry.setApplicationDestinationPrefixes("/pub");
//            registry.enableSimpleBroker("/sub");
//
//            registry.enableSimpleBroker("/lobby");
//
//        }
//    ////////고라니 END/////////
////    private final ChatHandler chatHandler;
////
////    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry){
////        registry.addHandler(chatHandler, "ws/chat").setAllowedOrigins("*");
////        //엔드포인트는 /chat, 같은 url에 대해서도 다른 요청을 하게끔 구별해준 항목
////    }
//
////    @Override
////    // 클라이언트가 메시지를 구독할 endpoint를 정의합니다.
////    public void configureMessageBroker(MessageBrokerRegistry config) {
//////        config.enableSimpleBroker("/send");
////        config.enableSimpleBroker("/topic", "/queue");
////        //토픽이나 큐로 시작하는 것을 메세지브로커로 라우팅 되게, 연결된 클라이언트에게 브로케스트(송신)함
////        config.setApplicationDestinationPrefixes("/num");
////        //num 시작 메세지를 컨트롤러의 메세지매핑 어노테이션 된 메세지 핸들러 메소드로 라우팅
////
////    }
////
////    @Override
////    // connection을 맺을때 CORS 허용합니다.
////    public void registerStompEndpoints(StompEndpointRegistry registry) {
//////        registry.addEndpoint("/").setAllowedOrigins("**").withSockJS(); 에러 발생
//////        registry.addEndpoint("/").setAllowedOriginPatterns("*").withSockJS();
////        registry.addEndpoint("/xhat").setAllowedOriginPatterns("*").withSockJS();
////        //핸드쉡이크 커넥션을 생성할 경로
////
////    }
//
//}
