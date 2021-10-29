package com.playssafy.playssafy.repository;

import com.playssafy.playssafy.dto.chat.Room;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class ChatRoomRepository {
    private Map<String, Room> roomMap;

    /**
     * @PostConstruct
     * 객체가 생성된 후 별도의 초기화 작업을 위해 실행되는 메소드. 객체 초기화를 위한다고 생각하면 된다.
     * 해당 메소드는 WAS가 띄워질 때 실행된다. 객체를 소멸시키는 @PreDestroy Annotation도 존재한다.
     */
    @PostConstruct
    private void init() {
        roomMap = new LinkedHashMap<>();
    }

    // 1. 전체 채팅방 반환 메서드
    public List<Room> findAllRooms() {
        List<Room> result = new ArrayList<>(roomMap.values());
        // 최신 순으로 반환한다.
        Collections.reverse(result);

        return result;
    }

    // 2. ID를 기반으로 채팅방을 찾는 메서드
    public Room findRoomById(String id) {
        return roomMap.get(id);
    }

    // 3. 채팅방을 생성하는 메서드
    public Room createRoom(String name) {
        Room room = Room.create(name);
        roomMap.put(room.getRoomId(), room);

        return room;
    }
}
