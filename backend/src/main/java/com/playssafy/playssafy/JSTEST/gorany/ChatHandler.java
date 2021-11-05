//package com.playssafy.playssafy.JSTEST.gorany;
///////////고라니///////////////
//import lombok.extern.log4j.Log4j2;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//import java.util.ArrayList;
//import java.util.List;
//
////다수의 클라이언트를 핸들링 하기위한 핸드르러, 텍스트 기반으로 해당 클래스 상속 중
////클라이언트로 부터 받은 메세지를 로그출력하고 환영 메세지를 보낸다
//@Component
//@Log4j2
//public class ChatHandler extends TextWebSocketHandler {
//
//    private static List<WebSocketSession> list = new ArrayList<>();
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message)throws Exception{
//        String payload = message.getPayload();
//        //전송되는 메세지에는  데이터, 헤더, 메타데이터, 체크 비트를 함께 보내 안정 증대되는데 이때 보내고자 한 데이터는 페이로드
//        log.info("payload : "+payload);
//        for(WebSocketSession sess:list){
//            sess.sendMessage(message);
//        }
//    }
//
//    //접속시 호출 메서드
//    @Override
//    public  void afterConnectionEstablished(WebSocketSession session)throws Exception{
//        list.add(session);
//        log.info(session+ " 클라이언트 접속");
//    }
//
//    //해제시
//    @Override
//    public  void  afterConnectionClosed(WebSocketSession session, CloseStatus status)throws Exception{
//        log.info(session+" 클라이언트 접속 해제");
//        list.remove(session);//인덱스 번호가 아닌데 어떻게 지우지????????????
//    }
//}
