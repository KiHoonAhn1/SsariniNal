package com.playssafy.playssafy.service;

import com.playssafy.playssafy.domain.Quiz.MindDto;
import com.playssafy.playssafy.domain.Quiz.MindEntity;
import com.playssafy.playssafy.domain.Quiz.SpeakDto;
import com.playssafy.playssafy.domain.Quiz.SpeakEntity;
import com.playssafy.playssafy.repository.MindRepository;
import com.playssafy.playssafy.repository.SpeakRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor // lombok. 클래스에 존재하는 모든 필드에 대한 생성자 자동 생성
@NoArgsConstructor // Parameter가 없는 생성자 자동 생성
public class  QuizService {
    @Autowired
    private MindRepository mindRepository;

    @Autowired
    private SpeakRepository speakRepository;

    @Transactional
    public List<MindDto> getMindQuiz(int quizCnt){
        List<MindEntity> mindEntityList = mindRepository.findAll();
        Collections.shuffle(mindEntityList);
        System.out.println(mindEntityList.size());
        List<MindDto> mindDtoList = new ArrayList<>();
        for(int i=0;i<quizCnt;i++){
            MindEntity mindEntity =mindEntityList.get(i);
            MindDto mindDto= new MindDto(mindEntity.getQuizNum(),mindEntity.getContents());
            mindDtoList.add(mindDto);
        }
        return mindDtoList;
    }

    @Transactional
    public void createMindQuiz(MindDto.RequestSetMindQuiz req){
        MindEntity mindEntity = MindEntity.builder()
                .contents(req.getContents())
                .build();
        mindRepository.save(mindEntity);
    }

    @Transactional
    public List<SpeakDto> getSpeakQuiz(int quizCnt){
        List<SpeakEntity> speakEntityList = speakRepository.findAll();
        Collections.shuffle(speakEntityList);
        List<SpeakDto> speakDtoList = new ArrayList<>();
        for(int i=0;i<quizCnt;i++){
            SpeakEntity speakEntity =speakEntityList.get(i);
            SpeakDto speakDto= new SpeakDto(speakEntity.getQuizNum(),speakEntity.getContents());
            speakDtoList.add(speakDto);
        }
        return speakDtoList;
    }

    @Transactional
    public void createSpeakQuiz(SpeakDto.RequestSetSpeakQuiz req){
        SpeakEntity speakEntity = SpeakEntity.builder()
                .contents(req.getContents())
                .build();
        speakRepository.save(speakEntity);
    }
}