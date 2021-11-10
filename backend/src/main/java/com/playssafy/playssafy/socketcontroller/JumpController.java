package com.playssafy.playssafy.socketcontroller;

import com.playssafy.playssafy.dto.game.JumpInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * 점프 관리 컨트롤러
 */
@Controller
@RequiredArgsConstructor
public class JumpController {
    private final SimpMessagingTemplate template;
//    private final GameRoomService gameRoomService; // 게임 방 저장소

    @MessageMapping(value = "/game/jumpsend")
    public void enter(JumpInfo jumpInfo) {
        System.out.println(jumpInfo.getRoomId());
//        System.out.println(jumpInfo.getJumpArr2().toString());
//        System.out.println(jumpInfo.toString());
//        System.out.println(jumpInfo.getJumpArr2().toString());
        System.out.println(jumpInfo.getClass().getSimpleName());
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }

}
