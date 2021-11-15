package com.playssafy.playssafy.socketcontroller;

import com.playssafy.playssafy.dto.ssafymind.MindMessage;
import com.playssafy.playssafy.dto.ssafymind.Point;
import com.playssafy.playssafy.dto.ssafymind.SsafyMind;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.service.SsafyMindService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Timer;
import java.util.TimerTask;

@Controller
@RequiredArgsConstructor
public class SsafyMindController {
    private final SimpMessagingTemplate template;
    private final SsafyMindService ssafyMindService; // 싸피마인드 서비스

    // 1. 싸피 마인드 입장
    @MessageMapping(value = "/ssafymind/enter")
    public void enter(Participant participant) {
        // 유저 입장 후 해당 게임 방 정보 얻기
        SsafyMind ssafyMind = ssafyMindService.enter(participant);
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/ssafymind/" + participant.getRoomId(), ssafyMind);
    }

    // 2. 싸피 마인드 퇴장
    @MessageMapping(value = "/ssafymind/exit")
    public void exit(Participant participant) {
        // 유저 입장 후 해당 게임 방 정보 얻기
        SsafyMind ssafyMind = ssafyMindService.exit(participant);
        // 방장이 퇴장한 경우 종료 메세지 뿌려주기
        if (ssafyMind == null) {
            template.convertAndSend("/ssafymind/" + participant.getRoomId(), "exit");
            return;
        }
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/ssafymind/" + participant.getRoomId(), ssafyMind);
    }

    // 3. 그림 데이터 서버 저장 및 교환
    @MessageMapping(value = "/ssafymind/draw/{roomId}")
    public void draw(@DestinationVariable String roomId, Point point) {
        // 우선 그리는 데이터 즉시 보내주기
        template.convertAndSend("/ssafymind/draw/" + roomId, point);
        // 그린 내용 서버에 저장(Redis 과부하 때문에 불가능).
        // ssafyMindService.draw(roomId, point);
    }

    // 4. 메세지 교환 로직
    @MessageMapping(value = "/ssafymind/message/{roomId}")
    public void answer(@DestinationVariable String roomId, MindMessage mindMessage) {
        // 메세지 정답 여부 판별 및 저장
        mindMessage = ssafyMindService.answer(roomId, mindMessage);
        // 메세지 데이터 보내주기
        template.convertAndSend("/ssafymind/message/" + roomId, mindMessage);
    }

    // 5. 시간 경과 로직
    @MessageMapping(value = "/ssafymind/time/{flag}")
    public void time(String roomId, @DestinationVariable String flag) {
        if(flag.equals("stop")) {
            // 시간 정지(stop)
            ssafyMindService.timeStop(roomId, flag);
        }
        else {
            // 시간 재개(start)
            ssafyMindService.timeStop(roomId, flag);
            // 타이머 시작
            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                // 문제당 주어진 시간초
                int cnt = 91;

                @Override
                public void run() {
                    // 서버 시간 데이터 변경
                    boolean flag = ssafyMindService.time(roomId, cnt--);
                    if (!flag) {
                        timer.cancel();
                        return;
                    }
                    if (cnt == -1) {
                        timer.cancel();
                        return;
                    }
                    // 시간 전송
                    template.convertAndSend("/ssafymind/time/" + roomId, cnt);
                }
            };
            // 1초마다 서버에 시간 전송
            timer.schedule(timerTask, 0, 1000);
        }
    }

    // 6. 플레이어 변경 로직
    @MessageMapping(value = "/ssafymind/change/player")
    public void changePlayer(String roomId) {
        int curPlayer = ssafyMindService.changePlayer(roomId);
        template.convertAndSend("/ssafymind/change/player/" + roomId, curPlayer);
    }

    // 7. 모달 닫기
    @MessageMapping(value = "/ssafymind/close/modal")
    public void closeModal(String roomId) {
        template.convertAndSend("/ssafymind/close/modal/" + roomId, false);
    }

    // 8. 다음 문제 갱신, 팀 및 순서 변경
    @MessageMapping(value = "/ssafymind/next/problem")
    public void nextProblem(String roomId) {
        SsafyMind ssafyMind = ssafyMindService.nextProblem(roomId);
        template.convertAndSend("/ssafymind/" + roomId, ssafyMind);
    }

    // 9. 게임 종료(점수 누적)
    @MessageMapping(value = "/ssafymind/end")
    public void end(String roomId) {
        // 게임 종료 로직 수행
        ssafyMindService.end(roomId);
    }
}
