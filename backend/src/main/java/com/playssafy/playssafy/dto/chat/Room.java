package com.playssafy.playssafy.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class Room {
    private String roomId; // 채팅 방 ID
    private String name; // 채팅 방 이름
    // Spring에서 WebSocket Connection이 맺어진 세션들을 HashSet으로 저장해둔다.
    private Set<WebSocketSession> sessions = new HashSet<>();

    // 채팅방 생성
    public static Room create(String name) {
        Room room = new Room();

        // UUID를 기반으로 방에 고유 식별자를 부여한다.
        room.roomId = UUID.randomUUID().toString();
        room.name = name;
        return room;
    }
}
