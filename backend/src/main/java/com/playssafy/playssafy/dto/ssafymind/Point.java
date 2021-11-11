package com.playssafy.playssafy.dto.ssafymind;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Point {
    private int x; // x좌표
    private int y; // y좌표
    private double size; // 선 굵기
    private String color; // 색
    private boolean fillFlag; // 채우기 여부
    private boolean beginPath; // 선 긋기 판단
}
