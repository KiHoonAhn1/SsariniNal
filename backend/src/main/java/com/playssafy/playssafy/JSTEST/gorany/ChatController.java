//package com.playssafy.playssafy.JSTEST.gorany;
//
//import com.playssafy.playssafy.dto.chat.Room;
//import lombok.extern.log4j.Log4j2;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import javax.websocket.OnMessage;
//import javax.websocket.OnOpen;
//import javax.websocket.Session;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@Log4j2
//public class ChatController {
////    @GetMapping("/xhat")//왜또 겟 매퍼야
////    public  void chatGET(){
////        log.info("@chatCtrler, chat get()");
////    }
//
//
////    //대기실 구성
////    private static final List<Session> session = new ArrayList<>();
////
////    @OnOpen
////    public void open(Session newUser) {
//////        System.out.println("connected");
////        log.info(newUser.getId()+"님 대기실 입장");
////        session.add(newUser);
//////        System.out.println(newUser.getId());
////
////    }
////
////
////    @OnMessage
////    public void getMsg(Session recieveSession, String msg) {
////        for (int i = 0; i < session.size(); i++) {
////            if (!recieveSession.getId().equals(session.get(i).getId())) {
////                try {
////                    session.get(i).getBasicRemote().sendText("상대 : "+msg);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }else{
////                try {
////                    session.get(i).getBasicRemote().sendText("나 : "+msg);
////                } catch (IOException e) {
////                    e.printStackTrace();
////                }
////            }
////        }
////    }
//
//    // /receive를 메시지를 받을 endpoint로 설정합니다.
//    @MessageMapping("/receive")
//    // /send로 메시지를 반환합니다.
//    @SendTo("/lobby")
//    // SocketHandler는 1) /receive에서 메시지를 받고, /send로 메시지를 보내줍니다.
//    // 정의한 SocketVO를 1) 인자값, 2) 반환값으로 사용합니다.
//    public List<Room> SocketHandler(String roomname) {
//        Room room = Room.create(roomname);
//        maproom.put(room.getRoomId(),room);
//
//        List<Room> roomList=new ArrayList<>();
//        Iterator iterator = maproom.entrySet().iterator();
//        while(iterator.hasNext()){
//            Map.Entry<String,Room> entry = (Map.Entry)iterator.next();
//            String roomid = (String)entry.getKey();
//            Room roomvalue = (Room)entry.getValue();
//            roomList.add(roomvalue);
//        }
//
////        출처: https://mine-it-record.tistory.com/233 [나만의 기록들]
////        // vo에서 getter로 userName을 가져옵니다.
////        String roomName = room.getName();
////        // vo에서 setter로 content를 가져옵니다.
////        String roomId = room.getRoomId();
////
////        // 생성자로 반환값을 생성합니다.
////        Room result = new Room(roomId, roomName);
////        // 반환
//        return roomList;
//    }
//    private Map<String, Room> maproom;
//}
