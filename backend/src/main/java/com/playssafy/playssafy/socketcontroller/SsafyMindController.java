package com.playssafy.playssafy.socketcontroller;

import com.playssafy.playssafy.dto.ssafymind.SsafyMind;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.service.SsafyMindService;
import lombok.RequiredArgsConstructor;
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
        // 게임 방 정보 소켓으로 반환
        template.convertAndSend("/ssafymind/" + participant.getRoomId(), ssafyMind);
    }
}
