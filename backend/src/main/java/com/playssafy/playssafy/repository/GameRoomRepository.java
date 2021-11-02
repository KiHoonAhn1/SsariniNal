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
    public GameRoom createRoom(String roomName, String userId) {
        GameRoom room = GameRoom.create(roomName, userId);
        roomMap.put(room.getId(), room);

        return room;
    }

    // 2. 게임 방 조회 메소드
    public GameRoom findRoomById(String roomId) {
        return roomMap.get(roomId);
    }

    // 3. 게임 방 참여 메소드
    public boolean joinRoom(String roomId, String userName) {
        GameRoom gameRoom = roomMap.get(roomId);
        // 방이 없는 경우 null 반환
        if (gameRoom == null)
            return false;
        // 유저 정보 추가
        gameRoom.getMembers().add(userName);
        return true;
    }

    // 4. 게임 방 퇴장 메소드
    public void exitRoom(String roomId, String userName) {
        GameRoom gameRoom = roomMap.get(roomId);
        // 유저 정보 조회(방장인지 확인)
        if (gameRoom.getHost().equals(userName)) {
            // 방장이라면 방 자체를 삭제 후 종료
            roomMap.remove(roomId);
            return;
        }
        // 방장이 아니라면 유저 정보만 삭제
        gameRoom.getMembers().remove(userName);
    }
}
