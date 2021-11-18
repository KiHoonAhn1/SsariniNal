package com.playssafy.playssafy.dto.waitroom;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;

@Setter
@Getter
@ToString
@AllArgsConstructor
public class Quiz {
    @Id
    private int id; // 문제 번호
    private String problem; // 문제 내용
    private String answer; // 문제 정답
}
