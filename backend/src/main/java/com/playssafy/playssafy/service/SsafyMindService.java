package com.playssafy.playssafy.service;


import com.playssafy.playssafy.dto.ssafymind.SsafyMind;
import com.playssafy.playssafy.dto.ssafymind.Team;
import com.playssafy.playssafy.dto.waitroom.InitGame;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.repository.SsafyMindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SsafyMindService {
    @Autowired
    private SsafyMindRepository ssafyMindRepository;
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    // 0. 게임방 생성 메서드
    public void createSsafyMind(InitGame initGame) {
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

        // 최종 결정된 순서 넣기
        for (Integer i : teams) {
            ssafyMind.getTeamOrder().add(i);
        }

        ssafyMindRepository.save(ssafyMind);
    }

    // 1. 게임방 입장 메서드
    @Transactional
    public synchronized SsafyMind enter(Participant participant) {
        System.out.println("come");

//        redisTemplate.execute(new SessionCallback() {
//            @Override
//            public Object execute(RedisOperations redisOperations) throws DataAccessException {
//                redisOperations.watch(participant.getRoomId());
//                SsafyMind ssafyMind = (SsafyMind) redisOperations.opsForValue().get("SsafyMind:" + participant.getRoomId());
//                System.out.println(ssafyMind);
//                redisOperations.multi();
//                return redisOperations.exec();
//            }
//        });

        SsafyMind ssafyMind = ssafyMindRepository.findById(participant.getRoomId()).get();
        // 방장이 아닌 경우에만
//        if (!ssafyMind.getHost().equals(participant.getParticipantId())) {
        // 방에 팀 정보 추가(해당 팀에 유저가 존재하지 않는 경우)
//        System.out.println(ssafyMind.getTeams().get(participant.getTeamNo()));
        System.out.println(ssafyMind);
        Team team = ssafyMind.getTeams().get(participant.getTeamNo());
        if (!team.getMembers().contains(participant))
            team.getMembers().add(participant);
        System.out.println(ssafyMind);
        System.out.println("done");
//        if(!ssafyMind.getTeams().get(participant.getTeamNo()).getMembers().contains(participant))
//            team.getMembers().add(participant);
//        ArrayList<Participant> team = new ArrayList<>();
//        if (team == null) {
//            team.add(participant);
//        }
//        else if (!team.contains(participant)) {
//            team.add(participant);
//        }
//        }

        // 변경 완료
        return ssafyMindRepository.save(ssafyMind);
    }

    // 2. 게임방 퇴장 메서드
    public SsafyMind exit(Participant participant) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(participant.getRoomId()).get();

        // 방장이라면 방 자체를 삭제 후 종료
        if (ssafyMind.getHost().equals(participant.getParticipantId())) {
            ssafyMindRepository.deleteById(participant.getRoomId());
            return null;
        }
        // 방장이 아니라면 유저 정보만 삭제
//        ssafyMind.getTeams().get(participant.getTeamNo()).remove(participant);

        // 변경 완료
        return ssafyMindRepository.save(ssafyMind);
    }


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
