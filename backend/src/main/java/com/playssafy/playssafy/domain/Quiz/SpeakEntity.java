package com.playssafy.playssafy.domain.Quiz;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.persistence.*;

@Getter
@Entity(name = "speak")
@NoArgsConstructor
@ApiModel
public class SpeakEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiParam(value = "문제 넘버",required = true, example = "1")
    private Long quizNum;

    @Column
    @NonNull
    @ApiModelProperty
    private String contents;

    @Builder
    public SpeakEntity(Long quizNum, @NonNull String contents) {
        this.quizNum = quizNum;
        this.contents = contents;
    }
}
