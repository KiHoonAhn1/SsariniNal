package com.playssafy.playssafy.repository;

import com.playssafy.playssafy.dto.game.GameRoom;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;
import java.util.Map;

@Repository
public class GameRoomRepository {
    private Map<String, GameRoom> roomMap;

    @PostConstruct
    private void init() {
        roomMap = new LinkedHashMap<>();
    }

    // 1. 게임방 생성 메소드
    public GameRoom createRoom(String roomName, String userName) {
        GameRoom room = GameRoom.create(roomName, userName);
        roomMap.put(room.getRoomId(), room);

        return room;
    }

    // 2. 게임방 조회 메소드
    public boolean findRoomById(String roomId) {
        return roomMap.containsKey(roomId);
    }
}
