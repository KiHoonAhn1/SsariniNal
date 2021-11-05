package com.playssafy.playssafy.dto.play;

import lombok.Data;

@Data
public class Team {
        private Long guestId;
        private String guestName;
        private int teamNum;

        public Team(Long guestId, String guestName, int teamNum) {
                this.guestId= guestId;
                this.guestName = guestName;
                this.teamNum = teamNum;
        }
}
