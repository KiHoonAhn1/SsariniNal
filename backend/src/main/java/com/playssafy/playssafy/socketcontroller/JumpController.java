package com.playssafy.playssafy.socketcontroller;


import com.playssafy.playssafy.dto.play.JumpInfo;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 점프 관리 컨트롤러
 */
@Controller
@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class JumpController {
//    @Autowired
//    private JumpService jumpService;

    private final SimpMessagingTemplate template;
//    private final GameRoomService gameRoomService; // 게임 방 저장소

    //점프 값 중계
    @MessageMapping(value = "/game/jump/data")
    public void sendJumpInfo(JumpInfo jumpInfo) {
        System.out.println("데이터");
        System.out.println(jumpInfo.toString());
//        System.out.println(jumpInfo.getClass().getSimpleName());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }

    //점프 값 중계
    @MessageMapping(value = "/game/jump/enter")
    public void sendEnter(JumpInfo jumpInfo) {
        System.out.println("입장");
        System.out.println(jumpInfo.toString());
//        System.out.println(jumpInfo.getClass().getSimpleName());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }

    //중지
    @MessageMapping(value = "/game/jump/stop")
    public void sendStop(JumpInfo jumpInfo) {
        System.out.println("중지");
        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }


    //게임 시작 : 장애물 동작
    @MessageMapping(value = "/game/jump/obstacle")
    public synchronized void sendOB(JumpInfo jumpInfo) {
//        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }

    //유저 입장
//    @MessageMapping(value = "/game/jump/enter")
//    public void enter(Participant participant) {
//        // 유저 입장 후 해당 게임 방 정보 얻기
//        WaitRoom gameRoom = waitRoomService.enterRoom(participant);
//        // 게임 방이 없는 경우 입장할 방이 없다고 알려주기
//        if (gameRoom == null) {
//            template.convertAndSend("/game/room/" + participant.getRoomId(), "null");
//            return;
//        }
//        // 게임방 정보 소켓으로 반환
//        template.convertAndSend("/game/room/" + participant.getRoomId(), gameRoom);
//    }
//
//    //방 퇴장
//    @MessageMapping(value = "/game/exit")
//    public void exit(Participant participant) {
//        // 퇴장하는 유저 방에서 제거해주기
//        WaitRoom gameRoom = waitRoomService.exitRoom(participant);
//        // 방장이 퇴장한 경우 종료 메세지 뿌려주기
//        if (gameRoom == null) {
//            template.convertAndSend("/game/room/" + participant.getRoomId(), "exit");
//            return;
//        }
//        // 게임방 정보 소켓으로 반환
//        template.convertAndSend("/game/room/" + participant.getRoomId(), gameRoom);
//    }
}
