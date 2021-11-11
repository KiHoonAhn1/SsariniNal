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
        // 그린 내용 서버에 저장
        ssafyMindService.draw(roomId, point);
    }

    // 4. 메세지 교환 로직
    @MessageMapping(value = "/ssafymind/answer/{roomId}")
    public void answer(@DestinationVariable String roomId, MindMessage mindMessage) {
        // 우선 메세지 데이터 보내주기
        template.convertAndSend("/ssafymind/answer/"  + roomId, mindMessage);
        // 서버에 메세지 스택 저장 및 정답 여부 판별
        boolean flag = ssafyMindService.answer(roomId, mindMessage);
        template.convertAndSend("/ssafymind/correct/"  + roomId, flag);
        // 정답이라면 새롭게 갱신된 방 정보 뿌려주기
//        if(flag)
//            template.convertAndSend("/ssafymind/" + roomId,);
    }
}
