package com.playssafy.playssafy.controller;

import com.playssafy.playssafy.dto.chat.ChatRoom;
import com.playssafy.playssafy.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
// 채팅방 정보를 관리할 컨트롤러
public class ChatRoomController {
    private final ChatRoomRepository repository;

    // 1. 전체 채팅 목록 조회.
    @GetMapping(value = "/rooms")
    public List<ChatRoom> rooms() {
        return repository.findAllRooms();
    }

    // 2. 채팅방 개설
    @PostMapping(value = "/room/{name}")
    public void create(@PathVariable String name) {
        repository.createRoom(name);
    }

    // 3. 채팅방 조회
    @GetMapping(value = "/room/{roomId}")
    public ChatRoom getRoom(String roomId) {
        return repository.findRoomById(roomId);
    }

    // 4. 채팅방 삭제
    @DeleteMapping(value = "/room/{roomId}")
    public void deleteRoom(@PathVariable String roomId){
        repository.deleteRoom(roomId);
    }
}
