package com.playssafy.playssafy.service;


import com.playssafy.playssafy.dto.ssafymind.*;
import com.playssafy.playssafy.dto.waitroom.InitGame;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.repository.SsafyMindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SsafyMindService {
    @Autowired
    private SsafyMindRepository ssafyMindRepository;

    // 0. 게임방 생성 메서드
    public void createSsafyMind(InitGame initGame) {
        System.out.println(ssafyMindRepository.findById(initGame.getRoomId()));
        if(!ssafyMindRepository.findById(initGame.getRoomId()).isEmpty())
            return;
        SsafyMind ssafyMind = new SsafyMind();

        // 방 ID 초기화
        ssafyMind.setRoomId(initGame.getRoomId());
        // 방장 정보 추가
        ssafyMind.setHost(initGame.getHost());

        // 팀 진행 순서 초기화
        boolean[] exist = initGame.getExist();
        // 현재 팀이 존재한다면, 팀 넣기
        List<Integer> teams = new ArrayList<>();
        for (int i = 1; i < exist.length; i++) {
            if (exist[i])
                teams.add(i);
        }
        // 팀 섞기(100번)
        for (int i = 0; i < 100; i++) {
            // 임의의 두 인덱스를 지정하고
            int a = (int) (Math.random() * teams.size());
            int b = (int) (Math.random() * teams.size());
            // 교환
            int temp = teams.get(a);
            teams.set(a, teams.get(b));
            teams.set(b, temp);
        }

        // 최종 결정된 순서 넣기(문제 개수 만큼)
        for(int i = 0; i < initGame.getQuizCnt(); i++) {
            for (Integer team : teams) {
                ssafyMind.getTeamOrder().add(team);
            }
        }

        // 테스트 문제 리스트 ////////
        ssafyMind.getQuizzes().add(new Quiz(1, "아미타불", "아미타불"));
        ssafyMind.getQuizzes().add(new Quiz(1, "원시천존", "원시천존"));
        ssafyMind.getQuizzes().add(new Quiz(1, "무량수불", "무량수불"));


        ssafyMindRepository.save(ssafyMind);
    }

    // 1. 게임방 입장 메서드
    public synchronized SsafyMind enter(Participant participant) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(participant.getRoomId()).get();
        Team team = ssafyMind.getTeams().get(participant.getTeamNo());
        if (!team.getMembers().contains(participant))
            team.getMembers().add(participant);

        // 변경 완료
        return ssafyMindRepository.save(ssafyMind);
    }

    // 2. 게임방 퇴장 메서드
    public synchronized SsafyMind exit(Participant participant) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(participant.getRoomId()).get();

        // 방장이라면 방 자체를 삭제 후 종료
        if (ssafyMind.getHost().equals(participant.getParticipantId())) {
            ssafyMindRepository.deleteById(participant.getRoomId());
            return null;
        }
        // 방장이 아니라면 유저 정보만 삭제
        Team team = ssafyMind.getTeams().get(participant.getTeamNo());
        team.getMembers().remove(participant);

        // 변경 완료
        return ssafyMindRepository.save(ssafyMind);
    }

    // 3. 그림 데이터 교환
    public void draw(String roomId, Point point) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();

        // 그림 데이터 추가
        ssafyMind.getPoints().add(point);
        ssafyMindRepository.save(ssafyMind);
    }

    // 4. 정답 여부 확인
    public synchronized boolean answer(String roomId, MindMessage mindMessage) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();

        // 메세지 스택 저장
        ssafyMind.getChat().add(mindMessage);
        ssafyMindRepository.save(ssafyMind);
        // 리스트의 마지막 부분 부터 문제 회수
        int lastIndex = ssafyMind.getQuizzes().size() - 1;
        if(ssafyMind.getQuizzes().get(lastIndex).getAnswer().equals(mindMessage.getMessage())) {
            // 마지막 문제를 제거하고
            ssafyMind.getQuizzes().remove(lastIndex);
            // 다음 팀으로 옮기고
            ssafyMind.getTeamOrder().remove(0);
            // 그린 그림 정보 삭제하고
            ssafyMind.getPoints().clear();
            System.out.println(ssafyMind.getQuizzes());
            // 저장한 뒤
            ssafyMindRepository.save(ssafyMind);
            // 정답
            return true;
        }
        // 오답
        return false;
    }

    // 5. 방을 순수하게 읽어오는 로직
    public SsafyMind read(String roomId) {
        return ssafyMindRepository.findById(roomId).get();
    }

    // 6. 1초마다 시간을 갱신하는 로직
    public synchronized boolean time(String roomId, int cnt) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
//        System.out.println("first : " + ssafyMind.getFirst());
//        System.out.println(ssafyMind.getCurTime());
        if(ssafyMind.getFirst() > 0 && ssafyMind.getCurTime() == 90) {
            ssafyMind.setFirst(ssafyMind.getFirst() - 1);
            ssafyMindRepository.save(ssafyMind);
            return false;
        }
        if(ssafyMind.getCurTime() == 90)
            ssafyMind.setFirst(ssafyMind.getFirst() + 1);
        ssafyMind.setCurTime(cnt);
        ssafyMindRepository.save(ssafyMind);
        return true;
    }

    // 7. 현재 저장된 시간을 가져오는 로직
//    public synchronized int getTime(String roomId) {
//        return ssafyMindRepository.findById(roomId).get().getCurTime();
//    }


//    @Autowired//자동주입
//    private QuizService quizService;

//    //게임 생성
//    public SsafyMind createGame(SsafyMind ssafyMind) throws Exception{
//        List<MindDto> quizzes= quizService.getMindQuiz(ssafyMind.getQuizIdx());
//        ssafyMind.setQuizzes(quizzes);
//        ssafyMind.setQuizIdx(0);
//        return ssafyMindRepository.save(ssafyMind);
//    }
//
//    //게임 종료
//    public boolean deleteGame(Long roomCode) throws Exception{
//        ssafyMindRepository.deleteById(roomCode);
//        Optional<SsafyMind> ssafyMind = ssafyMindRepository.findById(roomCode);
//        if (ssafyMind.isPresent()) {
//            return false;
//        }
//        else return true;
//    }
//
//    //다음 플레이 할 팀, 다음 문제 요청, 점수 갱신
//    public SsafyMind playNextTeam(SsafyMind ssafyMind) throws Exception{
//        //처음 시작시
//        if(ssafyMind.getQuizIdx()==0){
//            return ssafyMind;
//        }
//
//        //게임 종료
//        if(ssafyMind.getQuizIdx()==ssafyMind.getQuizzes().size()){
//            return null;
//        }
//
//        //게임 진행중
//        int nextTeam = ssafyMind.getPlayTeamIdx()+1;
//        if(nextTeam==ssafyMind.getTeamOrder().size()){
//            nextTeam=0;
//        }
//        int nextQuiz = ssafyMind.getQuizIdx()+1;
//        ssafyMind.setQuizIdx(nextQuiz);
//        ssafyMind.setPlayTeamIdx(nextTeam);
//        return ssafyMindRepository.save(ssafyMind);
//    }

    //점수 갱신, 요청  // 필요한가

    //채팅 기록
//    public List<String[]> chat(String roomCode, String guestId,String Sentence) throws Exception{
//        SsafyMind ssafyMind= ssafyMindRepository.findById(Long.parseLong(roomCode)).get();
//        List<String[]> chats= ssafyMind.getChat();
//        chats.add(new String[]{roomCode,guestId,Sentence});
//        ssafyMind.setChat(chats);
//        ssafyMindRepository.save(ssafyMind);
//        return chats;
//    }

//    //그림 좌표 기록, 요청
//    public SsafyMind drawGirin(String roomCode, String[] points) throws Exception{
//        SsafyMind ssafyMind= ssafyMindRepository.findById(Long.parseLong(roomCode)).get();
//        List<String[]> girin= ssafyMind.getPoints();
//        girin.add(points);
//        ssafyMind.setPoints(girin);
//        return ssafyMindRepository.save(ssafyMind);
//    }
}
