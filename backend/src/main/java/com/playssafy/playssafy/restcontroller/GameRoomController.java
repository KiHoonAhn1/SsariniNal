package com.playssafy.playssafy.restcontroller;

import com.playssafy.playssafy.dto.game.GameRoom;
import com.playssafy.playssafy.dto.game.Participant;
import com.playssafy.playssafy.repository.GameRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/game")
// 게임 방을 관리할 컨트롤러
public class GameRoomController {
    private final GameRoomRepository repository;

    /**
     * 게임 방 개설 및 개설 정보 반환
     */
    @PostMapping(value = "/create/{roomName}")
    public GameRoom createRoom(@PathVariable String roomName, @RequestBody Participant participant) {
        return repository.createRoom(roomName, participant);
    }

    /**
     * 게임 방 존재 여부 확인(ID로 확인)
     */
    @GetMapping(value = "/exist/{roomId}")
    public boolean readRoom(@PathVariable String roomId) {
        return repository.findRoomById(roomId);
    }

    /**
     * 게임 방 입장 조건 체크
     */
    @PostMapping(value = "/user")
    public int checkEnterRoom(@RequestBody Participant participant) {
        return repository.findParticipantByName(participant);
    }
}
