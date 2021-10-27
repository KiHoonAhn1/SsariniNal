package com.playssafy.playssafy.domain.Quiz;

import io.swagger.annotations.ApiParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class MindDto {
    @ApiParam(value = "문제 고유넘버 ", required = true, example = "123")
    private Long quizNum;
    @ApiParam(value = "문제 내용", required = true, example = "싸피")
    private String contents;

    public MindEntity toEntity(){
        MindEntity mindEntity= MindEntity.builder()
                .quizNum(quizNum)
                .contents(contents).build();
        return mindEntity;
    }

    public MindDto(Long quizNum, String contents) {
        this.quizNum = quizNum;
        this.contents = contents;
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class RequestSetMindQuiz{//pk값을 제외하고 주입하기위해 새로 만드는 디티오
        private String contents;
        public RequestSetMindQuiz(){

        }
    }
}
