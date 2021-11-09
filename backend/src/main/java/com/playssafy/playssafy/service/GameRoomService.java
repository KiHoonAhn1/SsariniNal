package com.playssafy.playssafy.service;

import com.playssafy.playssafy.dto.game.GameRoom;
import com.playssafy.playssafy.dto.game.Participant;
import com.playssafy.playssafy.repository.GameRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class GameRoomService {
    // Java
    // private Map<String, GameRoom> roomMap;
    // Redis
    @Autowired
    private GameRoomRepository gameRoomRepository;


//    @PostConstruct
//    private void init() {
//        roomMap = new LinkedHashMap<>();
//    }

    // 1. 게임방 생성 메소드
    public GameRoom createRoom(String roomName, Participant participant) {
        GameRoom room = new GameRoom();

        // UUID를 기반으로 방에 고유 식별자 부여.
        room.setId(UUID.randomUUID().toString());
        room.setName(roomName);

        // 방장 정보 추가
        room.setHost(participant.getParticipantId());

        // 해당 방의 정보 추가(Java)
        // roomMap.put(room.getId(), room);
        // 해당 방의 정보 추가(Redis)
        gameRoomRepository.save(room);

        return room;
    }

    // 2. 게임 방 참여 메소드
    public GameRoom enterRoom(Participant participant) {
        GameRoom gameRoom = gameRoomRepository.findById(participant.getRoomId()).get();
        // 방이 없는 경우 null 반환
        if (gameRoom == null) {
            return null;
        }
        // 참여자가 방장인 경우 사용자를 추가하지 않고 그대로 게임방 정보만 반환
        if (participant.getParticipantId() != null && gameRoom.getHost().equals(participant.getParticipantId()))
            return gameRoom;
        // 유저 정보 추가
        participant.setTeamNo(0); // 초기 팀 정보는 0번 : 관전자
        gameRoom.getMembers().add(participant);
        // 변경 완료
        gameRoomRepository.save(gameRoom);

        return gameRoom;
    }

    // 3. 게임 방 퇴장 메소드
    public GameRoom exitRoom(Participant participant) {
        GameRoom gameRoom = gameRoomRepository.findById(participant.getRoomId()).get();
        // 게임방이 존재하지 않는 경우
        if (gameRoom == null)
            return null;
        // 유저 정보 조회(방장인지 확인)
        if (gameRoom.getHost().equals(participant.getParticipantId())) {
            // 방장이라면 방 자체를 삭제 후 종료
            gameRoomRepository.deleteById(participant.getRoomId());
            return null;
        }
        // 방장이 아니라면 유저 정보만 삭제
        gameRoom.getMembers().remove(participant);
        // 변경 완료
        gameRoomRepository.save(gameRoom);
        return gameRoom;
    }

    // 4. 게임 방 입장 가능 여부 확인 메소드
    public int findParticipantByName(Participant participant) {
        GameRoom gameRoom = gameRoomRepository.findById(participant.getRoomId()).get();
        // 게임방이 존재하지 않는 경우
        if (gameRoom == null)
            return 0;
        // 입장 제한 인원을 초과한 경우
        if (gameRoom.getMembers().size() > 50)
            return 1;
        // 동일한 이름을 가진 참가자가 있는 경우
        if (gameRoom.getMembers().contains(participant))
            return 2;
        // 입장이 가능한 경우
        return 3;
    }

    // 5. 팀 변경 메소드
    public void changeTeam(GameRoom gameRoom) {
        gameRoomRepository.save(gameRoom);
        return;
    }
}
