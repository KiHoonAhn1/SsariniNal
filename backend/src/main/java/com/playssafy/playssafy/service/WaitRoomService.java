package com.playssafy.playssafy.service;

import com.playssafy.playssafy.dto.waitroom.InitGame;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import com.playssafy.playssafy.repository.WaitRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WaitRoomService {
    // Redis
    @Autowired
    private WaitRoomRepository waitRoomRepository;

    // 1. 게임방 생성 메소드
    public WaitRoom createRoom(String roomName, Participant participant) {
        WaitRoom room = new WaitRoom();

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // 테스트용 멤버 추가
//        room.getMembers().add(new Participant(participant.getRoomId(), "1", "김태현", 1));
//        room.getMembers().add(new Participant(participant.getRoomId(), "2", "김태현2", 1));
//        room.getMembers().add(new Participant(participant.getRoomId(), "3", "김태현3", 1));
//        room.getMembers().add(new Participant(participant.getRoomId(), "4", "이장섭", 2));
//        room.getMembers().add(new Participant(participant.getRoomId(), "5", "이장섭2", 2));
//        room.getMembers().add(new Participant(participant.getRoomId(), "6", "이장섭3", 2));
//        room.getMembers().add(new Participant(participant.getRoomId(), "7", "권희은", 5));
//        room.getMembers().add(new Participant(participant.getRoomId(), "8", "권희은2", 5));
//        room.getMembers().add(new Participant(participant.getRoomId(), "9", "권희은3", 5));
//        room.getMembers().add(new Participant(participant.getRoomId(), "10", "차은채", 7));
//        room.getMembers().add(new Participant(participant.getRoomId(), "11", "차은채2", 7));
//        room.getMembers().add(new Participant(participant.getRoomId(), "12", "차은채3", 7));
//        room.getMembers().add(new Participant(participant.getRoomId(), "13", "안기훈", 10));
//        room.getMembers().add(new Participant(participant.getRoomId(), "14", "안기훈2", 10));
//        room.getMembers().add(new Participant(participant.getRoomId(), "15", "안기훈3", 10));
//        room.setTeamline(new boolean[] {false, true, true, false, false, true, false, true, false, false, true});
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println(room.getMembers());

        // UUID를 기반으로 방에 고유 식별자 부여.
        room.setId(UUID.randomUUID().toString());
        // 게임 방 이름 설정
        room.setName(roomName);
        // 방장 정보 추가
        room.setHost(participant.getParticipantId());
        // 현재 게임 방 타입 저장(0) : 대기실
        room.setGameType(0);

        // 해당 방의 정보 추가(Redis)
        waitRoomRepository.save(room);

        return room;
    }

    // 2. 게임 방 참여 메소드
    public WaitRoom enterRoom(Participant participant) {
        WaitRoom waitRoom = waitRoomRepository.findById(participant.getRoomId()).get();
        // 방이 없는 경우 null 반환
        if (waitRoom == null) {
            return null;
        }
        // 참여자가 방장인 경우 사용자를 추가하지 않고 그대로 게임방 정보만 반환
        if (participant.getParticipantId() != null && waitRoom.getHost().equals(participant.getParticipantId()))
            return waitRoom;
        // 중복된 사람이 있는 경우 추가하지 않는다.
        if (waitRoom.getMembers().contains(participant))
            return waitRoom;
        // 유저 정보 추가
        participant.setTeamNo(0); // 초기 팀 정보는 0번 : 관전자
        waitRoom.getMembers().add(participant);
        // 변경 완료
        waitRoomRepository.save(waitRoom);

        return waitRoom;
    }

    // 3. 게임 방 퇴장 메소드
    public WaitRoom exitRoom(Participant participant) {
        WaitRoom waitRoom = waitRoomRepository.findById(participant.getRoomId()).get();
        // 게임방이 존재하지 않는 경우
        if (waitRoom == null)
            return null;
        // 유저 정보 조회(방장인지 확인)
        if (waitRoom.getHost().equals(participant.getParticipantId())) {
            // 방장이라면 방 자체를 삭제 후 종료
            waitRoomRepository.deleteById(participant.getRoomId());
            return null;
        }
        // 방장이 아니라면 유저 정보만 삭제
        waitRoom.getMembers().remove(participant);
        // 변경 완료
        waitRoomRepository.save(waitRoom);
        return waitRoom;
    }

    // 4. 게임 방 입장 가능 여부 확인 메소드
    public int findParticipantByName(Participant participant) {
        WaitRoom waitRoom = waitRoomRepository.findById(participant.getRoomId()).get();
        // 게임방이 존재하지 않는 경우
        if (waitRoom == null)
            return 0;
        // 입장 제한 인원을 초과한 경우
        if (waitRoom.getMembers().size() > 50)
            return 1;
        // 동일한 이름을 가진 참가자가 있는 경우
        if (waitRoom.getMembers().contains(participant))
            return 2;
        // 입장이 가능한 경우
        return 3;
    }

    // 5. 팀 변경 메소드
    public WaitRoom changeTeam(WaitRoom waitRoom) {
        return waitRoomRepository.save(waitRoom);
    }

    // 6. 게임 변경
    public void changeGame(InitGame initGame) {
        WaitRoom waitRoom = waitRoomRepository.findById(initGame.getRoomId()).get();
        waitRoom.setGameType(initGame.getGameType());
        waitRoomRepository.save(waitRoom);
    }

//    // 6. 팀 열기
//    public WaitRoom openTeam(String openTeams, String roomId) {
//        WaitRoom waitRoom = gameRoomRepository.findById(roomId).get();
////        waitRoom.setTeamline(openTeams);
//        return gameRoomRepository.save(waitRoom);
//    }
}
