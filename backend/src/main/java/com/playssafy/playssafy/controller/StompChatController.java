package com.playssafy.playssafy.controller;

import com.playssafy.playssafy.dto.chat.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
// 메세지 발행을 처리할 컨트롤러
public class StompChatController {
    private final SimpMessagingTemplate template; // 특정 Broker에게 메세지를 전달한다.

    /**
     * @MessageMapping Client가 Message를 Publish하는 경로.
     * Config에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합된다.
     */
    @MessageMapping(value = "/chat/enter")
    // 1. 입장 메세지를 보내는 메서드.
    public void enter(ChatMessage message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        /**
         * Client가 Message를 Subscribe하는 경로.
         * 해당 채널을 구독항 대상에게 메세지를 Broadcasting 해준다.
         */
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    // 2. 메세지를 보내는 메서드.
    @MessageMapping(value = "/chat/message")
    public void message(ChatMessage message) {
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}