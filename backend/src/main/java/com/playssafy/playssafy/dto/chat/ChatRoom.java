package com.playssafy.playssafy.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class ChatRoom {
    private String roomId; // 채팅 방 ID
    private String name; // 채팅 방 이름
    // Spring에서 WebSocket Connection이 맺어진 세션들을 HashSet으로 저장해둔다.
    private Set<WebSocketSession> sessions = new HashSet<>();


    // 채팅방 생성
    public static ChatRoom create(String name) {
        ChatRoom room = new ChatRoom();

        // UUID를 기반으로 방에 고유 식별자를 부여한다.
        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }
}
