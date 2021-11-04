package com.playssafy.playssafy.socketcontroller;

import com.playssafy.playssafy.dto.game.GameRoom;
import com.playssafy.playssafy.dto.game.Participant;
import com.playssafy.playssafy.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * 참가자 관리 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class ParticipantController {
    private final SimpMessagingTemplate template;
    private final GameRoomRepository gameRoomRepository; // 게임 방 저장소

    // 1. 게임 방 개설
//    @MessageMapping(value = "/game/create")
//    public void create(String roomName, Participant participant) {
//        GameRoom gameRoom = gameRoomRepository.createRoom(roomName, participant);
//    }

    // 2. 게임 방 입장
    @MessageMapping(value = "/game/enter")
    public void enter(Participant participant) {
        // 유저 입장 후 해당 게임 방 정보 얻기
        GameRoom gameRoom = gameRoomRepository.joinRoom(participant);
        // 게임방 정보 소켓으로 반환
        template.convertAndSend("/game/room/" + participant.getRoomId(), gameRoom);
    }

    // 3. 게임 방 퇴장
    @MessageMapping(value = "/game/exit")
    public void exit(Participant participant) {
        // 퇴장하는 유저 방에서 제거해주기
        GameRoom gameRoom = gameRoomRepository.exitRoom(participant);
        // 방장이 퇴장한 경우 종료 메세지 뿌려주기
        if(gameRoom == null)
            template.convertAndSend("/game/room/" + participant.getRoomId(), "exit");
        // 게임방 정보 소켓으로 반환
        template.convertAndSend("/game/room/" + participant.getRoomId(), gameRoom);
    }
}
