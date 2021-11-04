package com.playssafy.playssafy.repository;

import com.playssafy.playssafy.dto.game.GameRoom;
import com.playssafy.playssafy.dto.game.Participant;
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
    public GameRoom createRoom(String roomName, Participant participant) {
        GameRoom room = GameRoom.create(roomName, participant);
        roomMap.put(room.getId(), room);

        return room;
    }

    // 2. 게임 방 조회 메소드
    public boolean findRoomById(String roomId) {
        return roomMap.containsKey(roomId);
    }

    // 3. 게임 방 참여 메소드
    public GameRoom joinRoom(Participant participant) {
        GameRoom gameRoom = roomMap.get(participant.getRoomId());
        // 방이 없는 경우 null 반환
        if (gameRoom == null)
            return null;
        // 유저 정보 추가
        gameRoom.getMembers().add(participant);
        return gameRoom;
    }

    // 4. 게임 방 퇴장 메소드
    public GameRoom exitRoom(Participant participant) {
        GameRoom gameRoom = roomMap.get(participant.getRoomId());
        // 유저 정보 조회(방장인지 확인)
        if (gameRoom.getHost().equals(participant.getParticipantId())) {
            // 방장이라면 방 자체를 삭제 후 종료
            roomMap.remove(participant.getRoomId());
            return null;
        }
        // 방장이 아니라면 유저 정보만 삭제
        gameRoom.getMembers().remove(participant);
        return gameRoom;
    }
}
