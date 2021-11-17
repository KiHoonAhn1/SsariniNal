package com.playssafy.playssafy.dto.waitroom;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangeGame {
    private String roomId;
    private boolean ssafymindExplain;
    private boolean speakgameExplain;
    private boolean jumpgameExplain;
    private int gameType;
}
