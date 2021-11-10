package com.playssafy.playssafy.socketcontroller;

import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import com.playssafy.playssafy.service.WaitRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * 참가자 관리 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class WaitRoomSocketController {
    private final SimpMessagingTemplate template;
    private final WaitRoomService waitRoomService; // 게임 서비스

    // 1. 게임 방 입장
    @MessageMapping(value = "/game/enter")
    public void enter(Participant participant) {
        // 유저 입장 후 해당 게임 방 정보 얻기
        WaitRoom gameRoom = waitRoomService.enterRoom(participant);
        // 게임 방이 없는 경우 입장할 방이 없다고 알려주기
        if (gameRoom == null) {
            template.convertAndSend("/game/room/" + participant.getRoomId(), "null");
            return;
        }
        // 게임방 정보 소켓으로 반환
        template.convertAndSend("/game/room/" + participant.getRoomId(), gameRoom);
    }

    // 2. 게임 방 퇴장
    @MessageMapping(value = "/game/exit")
    public void exit(Participant participant) {
        // 퇴장하는 유저 방에서 제거해주기
        WaitRoom gameRoom = waitRoomService.exitRoom(participant);
        // 방장이 퇴장한 경우 종료 메세지 뿌려주기
        if (gameRoom == null) {
            template.convertAndSend("/game/room/" + participant.getRoomId(), "exit");
            return;
        }
        // 게임방 정보 소켓으로 반환
        template.convertAndSend("/game/room/" + participant.getRoomId(), gameRoom);
    }

    // 3. 팀 변환
    @MessageMapping(value = "/game/change")
    public void changeTeam(WaitRoom gameRoom) {
        gameRoom = waitRoomService.changeTeam(gameRoom);
        // 변경된 팀 정보 다시 유저들에게 뿌려주기
        template.convertAndSend("/game/room/" + gameRoom.getId(), gameRoom);
    }

    // 4. 게임으로 이동
    @MessageMapping(value = "/game/start/{gameIndex}")
    public void gameStart(String roomId, @DestinationVariable int gameIndex){
        template.convertAndSend("/game/start/" + roomId, gameIndex);
    }


//    @MessageMapping(value = "/game/openTeam/{roomId}")
//    public void openTeam(String openTeams, @DestinationVariable String roomId) {
//        WaitRoom gameRoom = gameRoomService.openTeam(openTeams, roomId);
//        // 바뀐 상태 다시 뿌려주기
//        template.convertAndSend("/game/room/" + roomId, gameRoom);
//    }
}
