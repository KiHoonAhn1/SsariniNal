package com.playssafy.playssafy.dto.game;

import lombok.Getter;
import lombok.Setter;

/**
 * 참가자 정보 관리 DTO
 */
@Getter
@Setter
public class Participant {
    private String roomId; // 게임방 ID
    private String participantId; // 참가자의 고유 ID
    private String participantName; // 참가자의 이름
    private int teamNo; // 참가자 소속 팀 번호

    @Override
    public boolean equals(Object obj) {
        // 비교하는 대상이 null인 경우 같지 않다.
        if (obj == null)
            return false;
        // 객체 타입이 다른 경우 같지 않다.
        if (this.getClass() != obj.getClass())
            return false;

        // 같은 경우를 정의한다.
        Participant that = (Participant) obj;
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
}
