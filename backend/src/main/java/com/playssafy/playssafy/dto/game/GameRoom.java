package com.playssafy.playssafy.dto.game;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.UUID;

@Getter
@Setter
public class GameRoom {
    private String id; // 게임 방 ID
    private String name; // 게임 방 이름
    private String host; // 방장
    private HashSet<String> members = new HashSet<>();

    // 게임 방 생성
    public static GameRoom create(String roomName, String userId){
        GameRoom room = new GameRoom();

        // UUID를 기반으로 방에 고유 식별자 부여.
        room.id = UUID.randomUUID().toString();
        room.name = roomName;

        // 방장 정보 추가
        room.members.add(userId);
        room.host = userId;

        return room;
    }
}
