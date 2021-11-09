package com.playssafy.playssafy.dto.game;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RedisHash("gameRoom")
public class GameRoom {
    @Id
    private String id; // 게임 방 ID
    private String name; // 게임 방 이름
    private String host; // 방장
//    private HashSet<Participant> members = new HashSet<>();
    private List<Participant> members = new ArrayList<>();
}
