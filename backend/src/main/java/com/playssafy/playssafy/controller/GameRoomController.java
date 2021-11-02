package com.playssafy.playssafy.controller;

import com.playssafy.playssafy.dto.game.GameRoom;
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
    @PostMapping(value = "/room/{roomName}/{userName}")
    public GameRoom createRoom(@PathVariable String roomName, @PathVariable String userName) {
        return repository.createRoom(roomName, userName);
    }

    /**
     * 게임 방 조회(ID로 조회)
     */
    @GetMapping(value = "/room/{roomId}")
    public GameRoom readRoom(@PathVariable String roomId) {
        return repository.findRoomById(roomId);
    }
}
