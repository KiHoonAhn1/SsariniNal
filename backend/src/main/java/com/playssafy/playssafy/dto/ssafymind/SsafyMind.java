package com.playssafy.playssafy.dto.ssafymind;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@RedisHash("ssafyMind")
public class SsafyMind {

    @Id
    //@Id 어노테이션은 JPA와 동일한 역할을 수행합니다. "member:{id}"의 위치에 자동으로 generate 값이 들어갑니다.
    private String roomId; // 방 ID
    private String host; // 방장 정보
    private List<Quiz> quizzes; // 퀴즈 정보 - 팀 수의 배수
    private List<Point> points; // x,y좌표값, 색정보, 굵기
    private int[] scores; // 팀당 점수(배열 형태)
    private List<MindMessage> chat; // 채팅정보
    private List<Team> teams; // 팀원 구성 정보
    private List<Integer> teamOrder; // 팀 진행 순서
    private int curPlayer; // 현재 진행중인 사람
    private int curTime; // 현재 시간
    private String timeFlag; // 시간 트리거
    private boolean[] curTeam; // 현재 열린 팀 정보
    private int curTeamCnt; // 현재 팀 개수

    public SsafyMind() {
        // 최대 10개 팀에 대응하는 팀 정보를 저장할 리스트 선언해두기
        int teamSize = 11;
        teams = new ArrayList<>();
        for (int i = 0; i < teamSize; i++) {
            teams.add(new Team());
            teams.get(i).setTeamNo(i);
        }
        // 팀당 점수 0점으로 초기화
        scores = new int[11];
        // 기타 동적 리스트 초기화
        quizzes = new ArrayList<>();
        points = new ArrayList<>();
        chat = new ArrayList<>();
        teamOrder = new ArrayList<>();
    }
}
