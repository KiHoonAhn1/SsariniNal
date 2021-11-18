package com.playssafy.playssafy.dto.speaking;

import com.playssafy.playssafy.dto.waitroom.Quiz;
import com.playssafy.playssafy.dto.ssafymind.Team;
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
@RedisHash("speaking")
public class Speaking {
    @Id
    private String roomId; // 방 ID
    private String host; // 방장 정보
    private List<Quiz> quizzes; // 퀴즈 정보 - 팀 수의 배수
    private int[] scores; // 팀당 점수(배열 형태)
    private List<SpeakMessage> chat; // 채팅정보
    private List<Team> teams; // 팀원 구성 정보
    private List<Integer> teamOrder; // 팀 진행 순서
    private int curPlayer; // 현재 진행중인 사람
    private boolean[] curTeam; // 현재 열린 팀 정보
    private int curTeamCnt; // 현재 팀 개수

    public Speaking() {
        // 최대 10개 팀에 대응하는 팀 정보를 저장할 리스트 선언해두기
        int teamSize = 11;
        teams = new ArrayList<>();
        for (int i = 0; i < teamSize; i++) {
            teams.add(new Team());
            teams.get(i).setTeamNo(i);
        }
        // 기타 동적 리스트 초기화
        quizzes = new ArrayList<>();
        chat = new ArrayList<>();
        teamOrder = new ArrayList<>();
    }
}
