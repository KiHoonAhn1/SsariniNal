package com.playssafy.playssafy.dto.play;

import com.playssafy.playssafy.domain.Quiz.MindDto;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.HashMap;
import java.util.List;

@Getter
@Data
@Builder
@NoArgsConstructor
@RedisHash("mind")
public class SsafyMind {

    @Id
    //@Id 어노테이션은 JPA와 동일한 역할을 수행합니다. "member:{id}"의 위치에 자동으로 generate 값이 들어갑니다.
    private Long roomCode;//방코드

    private List<MindDto> quizzes;//퀴즈 정보 - 팀 수의 배수
    private List<String[]> points;//x,y좌표값, 색정보, 굵기
    private HashMap<Integer,Integer> mindScore;//팀당 점수
    private List<String[]> chat;//채팅정보
    private List<Guest[]> teamOrder;//팀원구성과 게임 플레이 순으로 저장
    private int playTeamIdx;//현재 플레이 중인 팀의 인덱스
    private int playerIdx;//현재 플레이 중인 팀원의 인덱스
    private int quizIdx;//퀴즈 인덱스//이걸 처음 문제수로 하자

    @Override
    public String toString() {
        return "SsafyMind{" +
                "roomCode=" + roomCode +
                ", quizzes=" + quizzes +
                ", points=" + points +
                ", mindScore=" + mindScore +
                ", chat=" + chat +
                ", teamOrder=" + teamOrder +
                ", playTeamIdx=" + playTeamIdx +
                ", playerIdx=" + playerIdx +
                ", quizIdx=" + quizIdx +
                '}';
    }

    public SsafyMind(Long roomCode, List<MindDto> quizzes, List<String[]> points, HashMap<Integer, Integer> mindScore, List<String[]> chat, List<Guest[]> teamOrder, int playTeamIdx, int playerIdx, int quizIdx) {
        this.roomCode = roomCode;
        this.quizzes = quizzes;
        this.points = points;
        this.mindScore = mindScore;
        this.chat = chat;
        this.teamOrder = teamOrder;
        this.playTeamIdx = playTeamIdx;
        this.playerIdx = playerIdx;
        this.quizIdx = quizIdx;
    }
}
