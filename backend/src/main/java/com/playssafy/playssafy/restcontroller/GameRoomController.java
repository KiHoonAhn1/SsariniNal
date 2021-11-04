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
     * 게임 방 참여(ID로 참여), 게임 방 존재 여부 boolean으로 반환
     */
//    @PostMapping(value = "/join/{roomId}/{userName}")
//    public GameRoom joinRoom(@PathVariable String roomId, @PathVariable String userName) {
//        return repository.joinRoom(roomId, userName);
//    }

    /**
     * 게임 방 퇴장(ID로 퇴장), 게임방에서 자신의 정보 삭제.
     * 방장일 경우 방 전체 정보를 삭제.
     */
//    @DeleteMapping(value = "/exit/{roomId}/{userName}")
//    public void exitRoom(@PathVariable String roomId, @PathVariable String userName) {
//        repository.exitRoom(roomId, userName);
//    }
}
