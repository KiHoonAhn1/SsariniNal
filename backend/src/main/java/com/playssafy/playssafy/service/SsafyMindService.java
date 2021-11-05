package com.playssafy.playssafy.service;


import com.playssafy.playssafy.domain.Quiz.MindDto;
import com.playssafy.playssafy.dto.play.SsafyMind;
import com.playssafy.playssafy.repository.SsafyMindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SsafyMindService {
    @Autowired
    private SsafyMindRepository ssafyMindRepository;
    @Autowired//자동주입
    private QuizService quizService;
    //게임 생성
    public SsafyMind createGame(SsafyMind ssafyMind) throws Exception{
        List<MindDto> quizzes= quizService.getMindQuiz(ssafyMind.getQuizIdx());
        ssafyMind.setQuizzes(quizzes);
        ssafyMind.setQuizIdx(0);
        return ssafyMindRepository.save(ssafyMind);
    }

    //게임 종료
    public boolean deleteGame(Long roomCode) throws Exception{
        ssafyMindRepository.deleteById(roomCode);
        Optional<SsafyMind> ssafyMind = ssafyMindRepository.findById(roomCode);
        if (ssafyMind.isPresent()) {
            return false;
        }
        else return true;
    }

    //다음 플레이 할 팀, 다음 문제 요청, 점수 갱신
    public SsafyMind playNextTeam(SsafyMind ssafyMind) throws Exception{
        //처음 시작시
        if(ssafyMind.getQuizIdx()==0){
            return ssafyMind;
        }

        //게임 종료
        if(ssafyMind.getQuizIdx()==ssafyMind.getQuizzes().size()){
            return null;
        }

        //게임 진행중
        int nextTeam = ssafyMind.getPlayTeamIdx()+1;
        if(nextTeam==ssafyMind.getTeamOrder().size()){
            nextTeam=0;
        }
        int nextQuiz = ssafyMind.getQuizIdx()+1;
        ssafyMind.setQuizIdx(nextQuiz);
        ssafyMind.setPlayTeamIdx(nextTeam);
        return ssafyMindRepository.save(ssafyMind);
    }

    //점수 갱신, 요청  // 필요한가

    //채팅 기록
    public List<String[]> chat(String roomCode, String guestId,String Sentence) throws Exception{
        SsafyMind ssafyMind= ssafyMindRepository.findById(Long.parseLong(roomCode)).get();
        List<String[]> chats= ssafyMind.getChat();
        chats.add(new String[]{roomCode,guestId,Sentence});
        ssafyMind.setChat(chats);
        ssafyMindRepository.save(ssafyMind);
        return chats;
    }

    //그림 좌표 기록, 요청
    public SsafyMind drawGirin(String roomCode, String[] points) throws Exception{
        SsafyMind ssafyMind= ssafyMindRepository.findById(Long.parseLong(roomCode)).get();
        List<String[]> girin= ssafyMind.getPoints();
        girin.add(points);
        ssafyMind.setPoints(girin);
        return ssafyMindRepository.save(ssafyMind);
    }
}
