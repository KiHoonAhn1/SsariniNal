package com.playssafy.playssafy.socketcontroller;


import com.playssafy.playssafy.dto.play.JumpInfo;
import com.playssafy.playssafy.dto.ssazipjump.SsazipJump;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import com.playssafy.playssafy.service.SsazipJumpService;
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
//@RestController
//@RequestMapping("/")
public class SsazipJumpController {
    @Autowired
    private SsazipJumpService ssazipJumpService;
    private final SimpMessagingTemplate template;

    //0.룸, 게임 정보 요청
    @MessageMapping(value = "/game/jump/enter/reqInfoRoomNGame")
    public void reqInfoRoomNGame(Participant participant) {
        System.out.println("룸, 게임 정보 요청");
        // 입장 한 유저의 게임 방 정보 요청
        SsazipJump ssazipJump = ssazipJumpService.enter(participant);
        ssazipJump.setType(0);//초기 정보 확인을 위한 타입
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/game/jumpgame/" + participant.getRoomId(), ssazipJump);
    }

    //1.마스터에게 초기값 요청
    @MessageMapping(value = "/game/jump/enter/reqToMaster")
    public void needEnviroment(JumpInfo jumpInfo) {
    System.out.println("입장 전 정보 요청");
    System.out.println(jumpInfo.toString());
    template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }

    //7.현 상태 중계 마스터로부터
    @MessageMapping(value = "/game/jump/state")
    public void broadcastState(JumpInfo jumpInfo) {
        System.out.println("현 상태전송");
        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }
    //5. 입장
    @MessageMapping(value = "/game/jump/enter")
    public void sendEnter(JumpInfo jumpInfo) {
        System.out.println("입장");
        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }
    //2. 점프 값 중계, 3. 리셋 중계
    @MessageMapping(value = "/game/jump/data")
    public void sendJumpInfo(JumpInfo jumpInfo) {
        System.out.println("데이터");
        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }

    //중지
    @MessageMapping(value = "/game/jump/stop")
    public void sendStop(JumpInfo jumpInfo) {
        System.out.println("중지");
        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }


    //4.게임 시작 : 장애물 동작
    @MessageMapping(value = "/game/jump/obstacle")
    public synchronized void sendOB(JumpInfo jumpInfo) {
//        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }



//VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV
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


    //게임 스코어 입출력

    //라운드 정보 입출력

    //다음 라운드 생성

    //종료 및 점수 결산

    //모달 종료
}
