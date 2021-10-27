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
@Entity(name = "mind")
@NoArgsConstructor
@ApiModel
public class MindEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizNum;

    @Column
    @NonNull
    @ApiModelProperty
    private String contents;

    @Builder
    public MindEntity(Long quizNum, @NonNull String contents) {
        this.quizNum = quizNum;
        this.contents = contents;
    }
}
