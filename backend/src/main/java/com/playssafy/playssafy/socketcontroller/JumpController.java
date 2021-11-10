package com.playssafy.playssafy.socketcontroller;

import com.playssafy.playssafy.dto.game.GameRoom;
import com.playssafy.playssafy.dto.game.Participant;
import com.playssafy.playssafy.service.GameRoomService;
import lombok.Data;
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

    @MessageMapping(value = "/game/jump/enter")
    public void enter(JumpInfo jumpInfo) {
        template.convertAndSend("/game/jumpgame/" + jumpInfo.getRoomId(), jumpInfo);
    }

    @Data
    class JumpInfo{
        private String roomId; // 게임방 ID
        private boolean[] jumpArr; // 점프값 어레이
    }
}
