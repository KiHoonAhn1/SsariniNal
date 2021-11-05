package com.playssafy.playssafy.dto.play;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;
import java.util.HashMap;

@Getter
@Data
@Builder
@NoArgsConstructor
@RedisHash("room")
public class Room implements Serializable {

//    private static final long serialVersionUID = 664865927712847110L;


    @Id
    //@Id 어노테이션은 JPA와 동일한 역할을 수행합니다. "member:{id}"의 위치에 자동으로 generate 값이 들어갑니다.
    private Long roomCode;

    private String roomName;
    @Indexed
    private String masterID;//방장정보
    @Indexed
    private HashMap<Long, Team> guests;//게스트, 게스트팀정보
    private int gameState; //게임상태, 플레이 중인 게임 번호
    private HashMap<Integer, Integer> scores;//점수
//    public Member(String userId, String userName, boolean masterflag) {
//        this.userID = userId;
//        this.userName = userName;
//        this.masterflag = masterflag;
//    }



    @Override
    public String toString() {
        return "Room{" +
                "roomCode=" + roomCode +
                ", roomName='" + roomName + '\'' +
                ", masterID='" + masterID + '\'' +
                ", members=" + guests +
                ", gameState=" + gameState +
                ", scores=" + scores +
                '}';
    }

    public Room(Long roomCode, String roomName, String masterID, HashMap<Long, Team> members, int gameState, HashMap<Integer, Integer> scores) {
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.masterID = masterID;
        this.guests = guests;
        this.gameState = gameState;
        this.scores = scores;
    }
}
