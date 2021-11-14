package com.playssafy.playssafy.restcontroller;

import com.playssafy.playssafy.dto.waitroom.InitGame;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import com.playssafy.playssafy.service.SpeakingService;
import com.playssafy.playssafy.service.SsafyMindService;
import com.playssafy.playssafy.service.WaitRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/game")
// 게임 방을 관리할 컨트롤러
public class WaitRoomRestController {
    private final WaitRoomService waitRoomService;
    private final SsafyMindService ssafyMindService;
    private final SpeakingService speakingService;

    /**
     * 게임 방 개설 및 개설 정보 반환
     */
    @PostMapping(value = "/create/{roomName}")
    public WaitRoom createRoom(@PathVariable String roomName, @RequestBody Participant participant) {
        return waitRoomService.createRoom(roomName, participant);
    }

    /**
     * 게임 방 입장 조건 체크
     */
    @PostMapping(value = "/user")
    public int checkEnterRoom(@RequestBody Participant participant) {
        return waitRoomService.findParticipantByName(participant);
    }

    /**
     * 싸피 마인드 게임 방 생성
     */
    @PostMapping(value = "/create/ssafymind")
    public void createSsafyMind(@RequestBody InitGame initGame) {
        // 현재 게임 방 정보 갱신 로직 추가
        waitRoomService.changeGame(initGame);
        // ssafymind 게임방 생성
        ssafyMindService.createSsafyMind(initGame);
    }

    /**
     * 또박또박 말해요 게임 방 생성
     */
    @PostMapping(value = "/create/speaking")
    public void createSpeaking(@RequestBody InitGame initGame) {
        // 현재 게임 방 정보 갱신 로직 추가
        waitRoomService.changeGame(initGame);
        // 또박또박말해요 게임방 생성
        speakingService.createSpeaking(initGame);
    }

    /**
     * 싸집이 점프 게임 방 생성
     */
}
