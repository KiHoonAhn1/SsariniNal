package com.playssafy.playssafy.dto.play;

import lombok.Data;

import java.util.Arrays;

@Data
public class JumpInfo {
    private String roomId; // 게임방 ID
    private boolean[] jumpArr1; // 점프값 어레이
    private boolean[] jumpArr2;
    private boolean reloadflag;
    private String participantId; // 참가자의 고유 ID
    private String participantName; // 참가자의 이름
    private int teamNo; // 참가자 소속 팀 번호
    private boolean obstacleflag;
    private Integer[] xbArr;
    private Integer[] xbArrType;
    private int type;//데이터 정보 타입
    private boolean[] buserPresent1;
    private boolean[] buserPresent2;
    private boolean bGameStopFlag;//충돌 시 게임 중단 플레그

//    @Override
//    public String toString() {
//        return "JumpInfo{" +
//                "roomId='" + roomId + '\'' +
//                ", jumpArr1=" + Arrays.toString(jumpArr1) +
//                ", jumpArr2=" + Arrays.toString(jumpArr2) +
//                '}';
//    }
}
