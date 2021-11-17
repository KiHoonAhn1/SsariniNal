package com.playssafy.playssafy.dto.waitroom;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@RedisHash("gameRoom")
public class WaitRoom {
    @Id
    private String id; // 게임 방 ID
    private String name; // 게임 방 이름
    private String host; // 방장
    private boolean[] teamline; // 열린 팀 정보
    private List<Participant> members; // 유저 정보
    private int gameType; // 진행중인 게임 정보
    private int[] scores; // 총 점수 정보
    private boolean ssafymindExplain;
    private boolean speakgameExplain;
    private boolean jumpgameExplain;
    private boolean progress; // 현재 게임이 진행중인지 여부

    // 테스트용 멤버 초기화
    public WaitRoom() {
        members = new ArrayList<>();
        teamline = new boolean[11];
        scores = new int[11];
    }
}