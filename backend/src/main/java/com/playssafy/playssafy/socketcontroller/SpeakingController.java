package com.playssafy.playssafy.socketcontroller;

import com.playssafy.playssafy.dto.speaking.SpeakMessage;
import com.playssafy.playssafy.dto.speaking.Speaking;
import com.playssafy.playssafy.dto.speaking.Talking;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.service.SpeakingService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class SpeakingController {
    private final SimpMessagingTemplate template;
    private final SpeakingService speakingService; // 또박또박 말해요 서비스

    // 1. 또박또박 말해요 입장
    @MessageMapping(value = "/speaking/enter")
    public void enter(Participant participant) {
        // 유저 입장 후 해당 게임 방 정보 얻기
        Speaking speaking = speakingService.enter(participant);
        System.out.println(speaking);
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/speaking/" + participant.getRoomId(), speaking);
    }

    // 2. 또박또박 말해요 퇴장
    @MessageMapping(value = "/speaking/exit")
    public void exit(Participant participant) {
        // 유저 입장 후 해당 게임 방 정보 얻기
        Speaking speaking = speakingService.exit(participant);
        // 방장이 퇴장한 경우 종료 메세지 뿌려주기
        if (speaking == null && participant.isHost()) {
            template.convertAndSend("/speaking/" + participant.getRoomId(), "exit");
            return;
        }
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/speaking/" + participant.getRoomId(), speaking);
    }

    // 3. 중간 문장 전송
    @MessageMapping(value = "/speaking/talk/{roomId}")
    public void talk(@DestinationVariable String roomId, Talking talking) {
        // 중간 결과를 전송
        template.convertAndSend("/speaking/talk/" + roomId, talking);
    }

    // 4. 결과 문장 전송
    @MessageMapping(value = "/speaking/answer/{roomId}")
    public void answer(@DestinationVariable String roomId, SpeakMessage speakMessage) {
        speakMessage = speakingService.answer(roomId, speakMessage);
        // 보내주기
        template.convertAndSend("/speaking/answer/" + roomId, speakMessage);
    }

    // 5. 플레이어 변경
    @MessageMapping(value = "/speaking/change/player")
    public void changePlayer(String roomId) {
        int curPlayer = speakingService.changePlayer(roomId);
        template.convertAndSend("/speaking/change/player/" + roomId, curPlayer);
    }

    // 6. 다음 문제
    @MessageMapping(value = "/speaking/next/team")
    public void nextTeam(String roomId) {
        Speaking speaking = speakingService.nextTeam(roomId);
        template.convertAndSend("/speaking/" + roomId, speaking);
    }
}