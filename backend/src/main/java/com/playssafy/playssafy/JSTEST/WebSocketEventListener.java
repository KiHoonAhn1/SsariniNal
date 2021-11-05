//package com.playssafy.playssafy.JSTEST;
//
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.SimpMessageSendingOperations;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectedEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//
////이벤트리스터를 통해 소켓 연결, 끊기를 수신하여 참여, 떠남을 logging 하거나 브로드케스트한다
//@Component
////어노테이션은 자바 클래스를 스프링 빈이라고 표시하는 역할을 합니다. 이 어노테이션을 사용함으로써 스프링의 component-scanning 기술이 이 클래스를 어플리케이션 컨텍스트에 빈으로 등록하게 됩니다.
//public class WebSocketEventListener {
//    private static final Logger logger = LoggerFactory.getLogger(WebSocketEventListener.class);
//
//    @Autowired
//    private SimpMessageSendingOperations messagingTemplate;
//
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
//        logger.info("Received a new web socket connection");
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
//
//        String username = (String) headerAccessor.getSessionAttributes().get("username");
//        if(username != null) {
//            logger.info("User Disconnected : " + username);
//
//            SocketVoJs socketVoJs = new SocketVoJs();
////            socketVoJs.setType(MessageType.LEAVE);
//            socketVoJs.setUserName(username);
//
//            messagingTemplate.convertAndSend("/cre/public", socketVoJs);
//        }
//    }
//}
