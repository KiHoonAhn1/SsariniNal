package com.playssafy.playssafy.socketcontroller;


import com.playssafy.playssafy.dto.play.JumpInfo;
import com.playssafy.playssafy.dto.ssazipjump.SsazipJump;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import com.playssafy.playssafy.repository.SsazipJumpRepository;
import com.playssafy.playssafy.service.SsazipJumpService;
import com.playssafy.playssafy.service.WaitRoomService;
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
    private SsazipJumpRepository ssazipJumpRepository;
    private final SimpMessagingTemplate template;

    //0.마스터의 룸, 게임 정보 요청
    @MessageMapping(value = "/game/jump/enter/reqInfoRoomNGame")
    public void reqInfoRoomNGame(SsazipJump gotSsazipJump) {
        System.out.println("마스터의 룸, 게임 정보 요청");
        System.out.println(gotSsazipJump.toString());
        // 입장 한 유저의 게임 방 정보 요청
        SsazipJump ssazipJump = ssazipJumpService.read(gotSsazipJump.getRoomId());
        ssazipJump.setType(0);//초기 정보 확인을 위한 타입
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/game/jumpgame/" + gotSsazipJump.getRoomId(), ssazipJump);
    }
    //11. 유저 등록 및 송출
    @MessageMapping(value = "/game/jump/enter/reg/user")
    public void regUser(SsazipJump gotSsazipJump) {
        System.out.println("유저 등록 및 송출");
        System.out.println(gotSsazipJump.toString());
        SsazipJump ssazipJump = ssazipJumpService.regi(gotSsazipJump);
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }
    //12. 플레이어 값 저장, 유저에게 송출
    @MessageMapping(value = "/game/jump/enter/play")
    public void regPlayer(SsazipJump gotSsazipJump) {
        System.out.println("12. 플레이어 값 저장, 유저에게 송출");
        System.out.println(gotSsazipJump.toString());
        //저장
        SsazipJump ssazipJump = ssazipJumpService.regPlayer(gotSsazipJump);
//        //방장 유저의 게임 방 정보 요청
//        SsazipJump ssazipJump = ssazipJumpService.read(ressazipJump.getRoomId());
//        //중계할 데이터
//        ssazipJump.setBeUserPresent1(ressazipJump.getBeUserPresent1());
//        ssazipJump.setBeUserPresent2(ressazipJump.getBeUserPresent2());
//        ssazipJump.setObstacleflag(ressazipJump.isObstacleflag());
//        ssazipJump.setXbArr(ressazipJump.getXbArr());
//        ssazipJump.setXbArrType(ressazipJump.getXbArrType());
//        ssazipJump.setBeGameStopFlag(ressazipJump.isBeGameStopFlag());
//        ssazipJump.setType(ressazipJump.getType());
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }
    //10. 방 퇴장
    @MessageMapping(value = "/game/jump/exit")
    public void exit(Participant participant) {
        System.out.println("퇴장 감지");
        // 퇴장하는 유저 방에서 제거해주기
        SsazipJump ssazipJump = ssazipJumpService.exit(participant);

        // 방장이 퇴장한 경우 종료 메세지 뿌려주기
        if (ssazipJump == null) {
            System.out.println("방장 퇴장");
            template.convertAndSend("/game/jumpgame/" + participant.getRoomId(), "exit");
            return;
        }
        ssazipJump.setType(11);
        // 게임방 정보 소켓으로 반환
        template.convertAndSend("/game/jumpgame/" + participant.getRoomId(), ssazipJump);
    }

    //1.마스터에게 초기값 요청
    @MessageMapping(value = "/game/jump/enter/reqToMaster")
    public void needEnviroment(SsazipJump ssazipJump) {
    System.out.println("입장 전 정보 요청");
    System.out.println(ssazipJump.toString());
    template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }

    //7.현 상태 중계 마스터로부터
    @MessageMapping(value = "/game/jump/state")
    public void broadcastState(SsazipJump ssazipJump) {
        System.out.println("현 상태전송");
        System.out.println(ssazipJump.toString());
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }

    //2. 점프 값 중계, 3. 리셋 중계
    @MessageMapping(value = "/game/jump/data")
    public void sendJumpInfo(SsazipJump ssazipJump) {
        System.out.println("데이터");
        System.out.println(ssazipJump.toString());
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }

    //중지
    @MessageMapping(value = "/game/jump/stop")
    public void sendStop(SsazipJump ssazipJump) {
        System.out.println("중지");
        System.out.println(ssazipJump.toString());
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }


    //4.게임 시작 : 장애물 동작
    @MessageMapping(value = "/game/jump/obstacle")
    public synchronized void sendOB(SsazipJump ssazipJump) {
//        System.out.println(jumpInfo.toString());
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }

    //유저 공간
    //11. registry
    @MessageMapping(value = "/game/jump/enter/registry")
    public void registryDB(SsazipJump ressazipJump) {
        System.out.println("유저 접속 저장");
        System.out.println(ressazipJump.toString());
        // 입장 한 유저의 게임 방 정보 요청
        SsazipJump ssazipJump = ssazipJumpService.regi(ressazipJump);
//        ssazipJump.setType(0);//초기 정보 확인을 위한 타입
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/game/jumpgame/" + ressazipJump.getRoomId(), ssazipJump);
    }

    //게임 스코어 입출력

    //라운드 정보 입출력

    //다음 라운드 생성

    //종료 및 점수 결산

    //모달 종료
}
