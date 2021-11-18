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
public class SsazipJumpController {
    @Autowired
    private SsazipJumpService ssazipJumpService;
    private final SsazipJumpRepository ssazipJumpRepository;
    private final SimpMessagingTemplate template;
    //13. 가이드모달 시작시 필요 여부 확인 통신
    @MessageMapping(value = "/game/jump/modal/checker")
    public synchronized void modalChecker(SsazipJump gotSsazipJump) {
        System.out.println("13. 가이드모달 시작시 필요 여부 확인 통신");
        System.out.println(gotSsazipJump.toString());
        System.out.println(gotSsazipJump.getType());
        SsazipJump ssazipJump = ssazipJumpService.read(gotSsazipJump.getRoomId());
        System.out.println(ssazipJump.isGuideModalFlag());
        ssazipJump.setType(gotSsazipJump.getType());
        System.out.println(ssazipJump.toString());
        template.convertAndSend("/game/jumpgame/" + gotSsazipJump.getRoomId(), ssazipJump);
    }
    //0.mount : 마스터의 룸, 게임 정보 요청
    @MessageMapping(value = "/game/jump/enter/reqInfoRoomNGame")
    public synchronized void reqInfoRoomNGame(SsazipJump gotSsazipJump) {
        System.out.println("mount : 마스터의 룸, 게임 정보 요청");
        System.out.println(gotSsazipJump.toString());
        // 입장 한 유저의 게임 방 정보 요청
        SsazipJump ssazipJump = ssazipJumpService.read(gotSsazipJump.getRoomId());
        ssazipJump.setType(0);//초기 정보 확인을 위한 타입
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/game/jumpgame/" + gotSsazipJump.getRoomId(), ssazipJump);
    }
    //11. 유저(방장을 제외한 모든 참여자) 등록 및 송출
    @MessageMapping(value = "/game/jump/enter/reg/user")
    public synchronized void regUser(SsazipJump gotSsazipJump) {
        System.out.println("//11. 유저(방장을 제외한 모든 참여자) 등록 및 송출");
        System.out.println(gotSsazipJump.toString());
        SsazipJump ssazipJump = ssazipJumpService.regi(gotSsazipJump);
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }
    //12. 플레이어 값 저장, 유저에게 송출
    @MessageMapping(value = "/game/jump/enter/play")
    public synchronized void regPlayer(SsazipJump gotSsazipJump) {
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
    public synchronized void exit(Participant participant) {
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

    //40. 모달 종료
    @MessageMapping(value = "/game/jump/closemodal")
    public synchronized void closeModal(SsazipJump gotSsazipJump) {
        if(gotSsazipJump.getType()==41) System.out.println("가이드");
        else if(gotSsazipJump.getType()==42) System.out.println("스타트");
        else if(gotSsazipJump.getType()==43) System.out.println("라운드");
        System.out.println("모달 종료");
        SsazipJump ssazipJump = ssazipJumpService.read(gotSsazipJump.getRoomId());
        if(gotSsazipJump.getType()==41) {//가이드 종료
            ssazipJump.setGuideModalFlag(false);
        }
        if(gotSsazipJump.getType()==42){
            ssazipJump.setStartModalFlag(false);
        }
        if(gotSsazipJump.getType()==43){
            ssazipJump.setRoundModalFlag(false);
        }
//        if(gotSsazipJump.getType()==44){
//            ssazipJump.setRoundModalFlag(true   );
//        }
        System.out.println(gotSsazipJump.toString());
        ssazipJumpRepository.save(ssazipJump);
        ssazipJump.setType(gotSsazipJump.getType());
        template.convertAndSend("/game/jumpgame/" + gotSsazipJump.getRoomId(), ssazipJump);
    }

//    //1.마스터에게 초기값 요청
//    @MessageMapping(value = "/game/jump/enter/reqToMaster")
//    public synchronized void needEnviroment(SsazipJump ssazipJump) {
//    System.out.println("입장 전 정보 요청");
//    System.out.println(ssazipJump.toString());
//    template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
//    }

//    //7.현 상태 중계 마스터로부터
//    @MessageMapping(value = "/game/jump/state")
//    public synchronized void broadcastState(SsazipJump ssazipJump) {
//        System.out.println("현 상태전송");
//        System.out.println(ssazipJump.toString());
//        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
//    }

    //2. 점프 값 중계, 3. 리셋 중계
    @MessageMapping(value = "/game/jump/data")
    public synchronized void sendJumpInfo(SsazipJump ssazipJump) {
        System.out.println("데이터");
        System.out.println(ssazipJump.toString());
        template.convertAndSend("/game/jumpgame/" + ssazipJump.getRoomId(), ssazipJump);
    }

    //중지
    @MessageMapping(value = "/game/jump/stop")
    public synchronized void sendStop(SsazipJump gotSsazipJump) {
        System.out.println("중지");
        System.out.println(gotSsazipJump.toString());
        SsazipJump ssazipJump = ssazipJumpService.read(gotSsazipJump.getRoomId());
        ssazipJump.setBeGameStopFlag(true);
        ssazipJump.setLoser(gotSsazipJump.getLoser());
        ssazipJump.setLoseTeam(gotSsazipJump.getLoseTeam());
        ssazipJump.setNowRoundNum(gotSsazipJump.getNowRoundNum());
        ssazipJump.setGameScore1(gotSsazipJump.getGameScore1());
        ssazipJump.setGameScore2(gotSsazipJump.getGameScore2());
        ssazipJumpRepository.save(ssazipJump);
        ssazipJump.setType(gotSsazipJump.getType());
        template.convertAndSend("/game/jumpgame/" + gotSsazipJump.getRoomId(), ssazipJump);
    }
    //중지, 다음 배틀
    @MessageMapping(value = "/game/jump/stopbattle")
    public synchronized void sendStopNNextGame(SsazipJump gotSsazipJump) {
        System.out.println("다음 배틀");
        System.out.println(gotSsazipJump.getLoser());
        if(gotSsazipJump.getType()==62) System.out.println("all game done");
        System.out.println(gotSsazipJump.toString());
        SsazipJump ssazipJump=ssazipJumpService.read(gotSsazipJump.getRoomId());
        ssazipJump.setFinalScore(gotSsazipJump.getFinalScore());
        ssazipJump.setLoser(gotSsazipJump.getLoser());
        ssazipJump.setLoseTeam(gotSsazipJump.getLoseTeam());
        ssazipJump.setTeamIdx1(gotSsazipJump.getTeamIdx1());
        ssazipJump.setTeamIdx2(gotSsazipJump.getTeamIdx2());
        ssazipJump.setTeamOrder(gotSsazipJump.getTeamOrder());
        ssazipJump.setTeamOrderNext(gotSsazipJump.getTeamOrderNext());
        ssazipJump.setRemainRound(gotSsazipJump.getRemainRound());
        ssazipJump.setNextRemainRound(gotSsazipJump.getNextRemainRound());
        ssazipJumpRepository.save(ssazipJump);
        ssazipJump.setType(gotSsazipJump.getType());
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
    public synchronized void registryDB(SsazipJump ressazipJump) {
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
