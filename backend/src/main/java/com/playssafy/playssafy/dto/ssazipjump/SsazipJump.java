package com.playssafy.playssafy.dto.ssazipjump;

import com.playssafy.playssafy.dto.ssafymind.Team;
import com.playssafy.playssafy.dto.waitroom.Participant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 점핑 게임, 참가자 정보 관리 DTO new
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@RedisHash("ssazipJump")
public class SsazipJump {

    @Id
    //@Id 어노테이션은 JPA와 동일한 역할을 수행합니다. "member:{id}"의 위치에 자동으로 generate 값이 들어갑니다.
    //용어 사전 -> 팀끼리 3번 붙는 것은 게임, 게임 스코어 라 하며, 8강 4강 등은 라운드라 명한다.
    // 3판 2선승을 위한 변수 gameScore1, gameScore2
    //  teams -> 그 라운드(강)을 담는 변수, 홀수시 봇(부전승 용 상대)
    //  팀원이 없다면 상대 팀은 승리한다.
    private String roomId; // 방 ID
    private String host; // 방장 정보
    private int gameScore1;// 1팀의 선승 값
    private int gameScore2;// 2팀의 선승 값
    private List<Team> teamsMember; // 팀별 팀원 구성 초기 정보, idx=0 은 공란, 멤버스는 participants
    private List<Integer> teamOrder; // 팀 진행 순서 => 봇은 teamNo =-2
    private List<Integer> teamOrderNext; // 현 라운드에서 승리하여 다음라운드로 올라간 팀
    private List<Integer> finalScore; // 최종 점수(배열 형태) {1등 팀 번호, 2등 , 3등, 3등}
    private int remainRound;//현재 라운드 계산 값 => 강
    private int nextRemainRound;//다음 라운드 계산 값 => 다음 강

    private int teamIdx1; // 현재 진행 중 팀 1의 팀 인덱스
    private int teamIdx2;
    private boolean masterCreatedFlag;//마스터의 선 입장 확인용
    // 팀스맴버는 1팀부터 10팀까지 파티스펜트리스트 정 배열
    // 팀 오더는 이번 라운드 진행될 팀 번호 (ex, 4 2 1 3)=> 4강이며 4,2이 배틀, 1,3이 배틀
    // 팀 인덱스는 팀 오더를 가리키는 값, 인덱스

    //from JumpInfo class
    private boolean[] jumpArr1; // 점프값 어레이
    private boolean[] jumpArr2;
    private boolean reloadflag;
    private String participantId; // 참가자의 고유 ID
    private String participantName; // 참가자의 이름
    private int teamNo; // 참가자 소속 팀 번호
    private boolean obstacleflag;//장애물 시작 플레그
    private Integer[] xbArr;//장애물 x위치 어레이
    private Integer[] xbArrType;//장애물 타입 어레이
    private int type;//데이터 정보 타입
    //참가 여부 어레이
    private boolean[] beUserPresent1;
    private boolean[] beUserPresent2;//참가 여부 어레이
    private boolean beGameStopFlag;//충돌 시 게임 중단 플레그
    private String lastEntering;//최근 입장자
    private String loser;//걸린이
    private String loseTeam;//걸린 팀
    private int nowRoundNum;//현 게임의 몇 판 째인지

    //modal flag
    private boolean guideModalFlag;
    private boolean startModalFlag;
    private boolean roundModalFlag;



    public SsazipJump() {
        // 최대 10개 팀에 대응하는 팀 정보를 저장할 리스트 선언해두기
        int teamSize = 11;
        teamsMember = new ArrayList<>();
        for (int i = 0; i < teamSize; i++) {
            teamsMember.add(new Team());
            teamsMember.get(i).setTeamNo(i);
        }
        // 동적 리스트 초기화
        teamOrder = new ArrayList<>();
        teamOrderNext = new ArrayList<>();
        finalScore = new ArrayList<>();
    }


    @Override
    public String toString() {
        return "SsazipJump{" +
                "roomId='" + roomId + '\'' +
                ", type=" + type +
                ", participantName='" + participantName + '\'' +
                ", beUserPresent1=" + Arrays.toString(beUserPresent1) +
                ", beUserPresent2=" + Arrays.toString(beUserPresent2) +
                ", host='" + host + '\'' +
                ", gameScore1=" + gameScore1 +
                ", gameScore2=" + gameScore2 +
                ", teamOrder=" + teamOrder +
                ", teamOrderNext=" + teamOrderNext +
                ", finalScore=" + finalScore +
                ", remainRound=" + remainRound +
                ", nextRemainRound=" + nextRemainRound +
                ", teamIdx1=" + teamIdx1 +
                ", teamIdx2=" + teamIdx2 +
                ", masterCreatedFlag=" + masterCreatedFlag +
                ", jumpArr1=" + Arrays.toString(jumpArr1) +
                ", jumpArr2=" + Arrays.toString(jumpArr2) +
                ", reloadflag=" + reloadflag +
                ", participantId='" + participantId + '\'' +

                ", teamNo=" + teamNo +
                ", obstacleflag=" + obstacleflag +
                ", xbArr=" + Arrays.toString(xbArr) +
                ", xbArrType=" + Arrays.toString(xbArrType) +


                ", beGameStopFlag=" + beGameStopFlag +
                ", lastEntering='" + lastEntering + '\'' +
                ", loser='" + loser + '\'' +
                ", loseTeam='" + loseTeam + '\'' +
                ", nowRoundNum=" + nowRoundNum +
                ", guideModalFlag=" + guideModalFlag +
                ", startModalFlag=" + startModalFlag +
                ", roundModalFlag=" + roundModalFlag +
                '}';
    }
}
