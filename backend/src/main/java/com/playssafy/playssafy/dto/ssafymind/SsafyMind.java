package com.playssafy.playssafy.dto.ssafymind;

import com.playssafy.playssafy.domain.Quiz.MindDto;
import com.playssafy.playssafy.dto.waitroom.Participant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.*;

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
    private List<MindDto> quizzes; // 퀴즈 정보 - 팀 수의 배수
    private List<String[]> points; // x,y좌표값, 색정보, 굵기
    // private HashMap<Integer, Integer> mindScore; // 팀당 점수
    private int[] mindScore; // 팀당 점수(배열 형태)
    private List<String> chat; // 채팅정보
    // private List<Guest[]> teamOrder; // 팀원구성과 게임 플레이 순으로 저장
    private ArrayList<ArrayList<Participant>> teams; // 팀원 구성 정보
    private List<Integer> teamOrder; // 팀 진행 순서
    private int curTeam; // 현재 진행중인 팀
    private List<Integer> playerOrder; // 팀 내 진행 순서
    private int curPlayer; // 현재 진행중인 사람

    public SsafyMind() {
        // 최대 10개 팀에 대응하는 팀 정보를 저장할 리스트 선언해두기
        int teamSize = 11;
        teams = new ArrayList<>();
        for (int i = 0; i < teamSize; i++) {
            teams.add(new ArrayList<>());
        }
        // 기타 동적 리스트 초기화
        quizzes = new ArrayList<>();
        points = new ArrayList<>();
        chat = new ArrayList<>();
        teamOrder = new ArrayList<>();
        playerOrder = new ArrayList<>();
    }


//    @Override
//    public String toString() {
//        return "SsafyMind{" +
//                "roomCode=" + roomCode +
//                ", quizzes=" + quizzes +
//                ", points=" + points +
//                ", mindScore=" + mindScore +
//                ", chat=" + chat +
//                ", teamOrder=" + teamOrder +
//                ", playTeamIdx=" + playTeamIdx +
//                ", playerIdx=" + playerIdx +
//                ", quizIdx=" + quizIdx +
//                '}';
//    }

//    public SsafyMind(Long roomCode, List<MindDto> quizzes, List<String[]> points, HashMap<Integer, Integer> mindScore, List<String[]> chat, List<Guest[]> teamOrder, int playTeamIdx, int playerIdx, int quizIdx) {
//        this.roomCode = roomCode;
//        this.quizzes = quizzes;
//        this.points = points;
//        this.mindScore = new int[11]; // 10개의 팀에 대한 배열
//        this.chat = chat;
//        this.teamOrder = teamOrder;
//        this.playTeamIdx = playTeamIdx;
//        this.playerIdx = playerIdx;
//        this.quizIdx = quizIdx;
//    }
}