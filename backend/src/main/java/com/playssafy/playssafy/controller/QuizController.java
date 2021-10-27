package com.playssafy.playssafy.controller;

import com.playssafy.playssafy.domain.Quiz.MindDto;
import com.playssafy.playssafy.domain.Quiz.SpeakDto;
import com.playssafy.playssafy.service.QuizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = {"2.Quiz"})
@RestController // REST API를 사용한다는 것을 알려준다.
@RequestMapping("/") // 요청의 접두사 설정
public class QuizController {
    @Autowired//자동주입
    private QuizService quizService;

    /*싸피마인드 문제요청*/
    @ApiOperation(value = "싸피마인드 문제 요청")
    @GetMapping(value = "quiz/mind/{quiz_cnt}")
    public ResponseEntity<List<MindDto>> getMindQuiz(
            @ApiParam(value = "요청 할 문제 수",example = "10")
            @PathVariable(name = "quiz_cnt") int quizCnt
    ) throws Exception{
        List<MindDto> mindquiz = quizService.getMindQuiz(quizCnt);
        return ResponseEntity.ok().body(mindquiz);
    }
    /*싸피마인드 문제만들기*/
    @ApiOperation(value = "싸피마인드 문제 만들기")
    @PostMapping(value = "quiz/mind")
    public void makeMindQuiz(
            @ApiParam(value = "문제 만들기")
            @RequestBody MindDto.RequestSetMindQuiz request
    ) throws Exception{
        System.out.println(request.toString());
        System.out.println("=-==="+request.getContents());
        quizService.createMindQuiz(request);
    }

    /*또박또박 문제요청*/
    @ApiOperation(value = "또박또박 문제 요청")
    @GetMapping(value = "quiz/speak/{quiz_cnt}")
    public ResponseEntity<List<SpeakDto>> getSpeakQuiz(
            @ApiParam(value = "요청 할 문제 수",example = "10")
            @PathVariable(name = "quiz_cnt") int quizCnt
    ) throws Exception{
        List<SpeakDto> speakDtoList = quizService.getSpeakQuiz(quizCnt);
        return ResponseEntity.ok().body(speakDtoList);
    }
    /*또박또박 문제만들기*/
    @ApiOperation(value = "또박또박 문제 만들기")
    @PostMapping(value = "quiz/speak")
    public void makeMindQuiz(
            @ApiParam(value = "문제 만들기")
            @RequestBody SpeakDto.RequestSetSpeakQuiz request
    ) throws Exception{
        quizService.createSpeakQuiz(request);
    }

}
