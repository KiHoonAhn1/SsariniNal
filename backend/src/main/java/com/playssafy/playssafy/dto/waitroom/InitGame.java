package com.playssafy.playssafy.dto.waitroom;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Setter
@Getter
@ToString
// 방 초기화용 DTO 객체
public class InitGame {
    @Id
    private String roomId;
    private String host;
    private boolean[] exist; // 열린 팀 정보
    private int gameType; // 게임 타입
    private int quizCnt; // 퀴즈 개수
}
