package com.playssafy.playssafy.dto.speaking;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SpeakMessage {
  private String name; // 보낸 사람 이름
  private int teamNo; // 보낸 사람 팀 번호
  private String message; // 보낸 내용
  private boolean correct; // 정답 여부
}
