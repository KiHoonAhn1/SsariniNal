package com.playssafy.playssafy.dto.game;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.UUID;

@Getter
@Setter
public class GameRoom {
    private String roomId; // 게임 방 ID
    private String roomName; // 게임 방 이름
    private HashSet<String> users = new HashSet<>();

    // 게임 방 생성
    public static GameRoom create(String roomName, String userName){
        GameRoom room = new GameRoom();

        // UUID를 기반으로 방에 고유 식별자 부여.
        room.roomId = UUID.randomUUID().toString();
        room.roomName = roomName;

        // 방장 정보 추가
        room.users.add(userName);

        return room;
    }
}
