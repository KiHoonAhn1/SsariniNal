package com.playssafy.playssafy.dto.play;

import com.playssafy.playssafy.dto.waitroom.Participant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

/**
 * 점핑 게임, 참가자 정보 관리 DTO
 */
@Getter
@Setter
@AllArgsConstructor
public class JumpInfo {
    private String roomId; // 게임방 ID
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
    private boolean bGameStopFlag;//충돌 시 게임 중단 플레그

    @Override
    public boolean equals(Object obj) {
        // 비교하는 대상이 null인 경우 같지 않다.
        if (obj == null)
            return false;
        // 객체 타입이 다른 경우 같지 않다.
        if (this.getClass() != obj.getClass())
            return false;

        // 같은 경우를 정의한다.
        JumpInfo that = (JumpInfo) obj;
        // 참가자 아이디가 같은 경우 같다.
        if (this.participantId.equals(that.participantId))
            return true;
        // 참가자 이름이 같은 경우 같다
        if (this.participantName.equals(that.participantName))
            return true;
        // 그 외의 경우 모두 다르다.
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;

        // roomId, participantName이 정확히 일치하는 경우 HashCode가 일치한다.
        hashCode = prime * hashCode + ((roomId == null) ? 0 : roomId.hashCode());
        hashCode = prime * hashCode + ((participantName == null) ? 0 : participantName.hashCode());

        return hashCode;
    }

    @Override
    public String toString() {
        return "JumpInfo{" +
                "roomId='" + roomId + '\'' +
                ", jumpArr1=" + Arrays.toString(jumpArr1) +
                ", jumpArr2=" + Arrays.toString(jumpArr2) +
                ", reloadflag=" + reloadflag +
                ", participantId='" + participantId + '\'' +
                ", participantName='" + participantName + '\'' +
                ", teamNo=" + teamNo +
                ", obstacleflag=" + obstacleflag +
                ", xbArr=" + Arrays.toString(xbArr) +
                ", xbArrType=" + Arrays.toString(xbArrType) +
                ", type=" + type +
                ", beUserPresent1=" + Arrays.toString(beUserPresent1) +
                ", beUserPresent2=" + Arrays.toString(beUserPresent2) +
                ", bGameStopFlag=" + bGameStopFlag +
                '}';
    }
}
