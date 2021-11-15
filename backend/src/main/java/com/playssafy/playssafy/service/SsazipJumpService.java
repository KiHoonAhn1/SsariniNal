//package com.playssafy.playssafy.service;
//
//import com.playssafy.playssafy.dto.ssafymind.*;
//import com.playssafy.playssafy.dto.ssazipjump.SsazipJump;
//import com.playssafy.playssafy.dto.waitroom.InitGame;
//import com.playssafy.playssafy.dto.waitroom.Participant;
//import com.playssafy.playssafy.repository.SsazipJumpRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class SsazipJumpService {
//    @Autowired
//    private SsazipJumpRepository ssazipJumpRepository;
//
//    // 0. 게임방 생성 메서드
//    public void createSsazipJump(InitGame initGame) {
//        if(!ssazipJumpRepository.findById(initGame.getRoomId()).isEmpty())
//            return;
//        SsazipJump ssazipJump = new SsazipJump();
//
//        // 방 ID 초기화
//        ssazipJump.setRoomId(initGame.getRoomId());
//        // 방장 정보 추가
//        ssazipJump.setHost(initGame.getHost());
//
//        // 팀 진행 순서 초기화
//        boolean[] exist = initGame.getExist();
//        // 현재 팀이 존재한다면, 팀 넣기
//        List<Integer> teams = new ArrayList<>();
//        for (int i = 1; i < exist.length; i++) {
//            if (exist[i])
//                teams.add(i);
//        }
//        // 팀 섞기(100번)
//        for (int i = 0; i < 100; i++) {
//            // 임의의 두 인덱스를 지정하고
//            int a = (int) (Math.random() * teams.size());
//            int b = (int) (Math.random() * teams.size());
//            // 교환
//            int temp = teams.get(a);
//            teams.set(a, teams.get(b));
//            teams.set(b, temp);
//        }
//
//        // 최종 결정된 순서 넣기(문제 개수 만큼)
//        for(int i = 0; i < initGame.getQuizCnt(); i++) {
//            for (Integer team : teams) {
//                ssafyMind.getTeamOrder().add(team);
//            }
//        }
//        // 현재 팀 개수 초기화
//        ssafyMind.setCurTeamCnt(teams.size());
//
//        // 테스트 문제 리스트 ////////
//        for(int i = 0; i < 20; i++) {
//            ssafyMind.getQuizzes().add(new Quiz(1, "아미타불", "아미타불"));
//            ssafyMind.getQuizzes().add(new Quiz(1, "원시천존", "원시천존"));
//            ssafyMind.getQuizzes().add(new Quiz(1, "무량수불", "무량수불"));
//        }
//        // 팀 초기화////////
//        ssafyMind.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "1", "김태현1", 1));
//        ssafyMind.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "2", "김태현2", 1));
//        ssafyMind.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "3", "김태현3", 1));
//        ssafyMind.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "4", "김태현4", 1));
//        ssafyMind.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "1", "이장섭1", 2));
//        ssafyMind.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "2", "이장섭2", 2));
//        ssafyMind.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "3", "이장섭3", 2));
//        ssafyMind.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "4", "이장섭4", 2));
//
//
//        ssafyMindRepository.save(ssafyMind);
//    }
//
//    // 1. 게임방 입장 메서드
//    public synchronized SsafyMind enter(Participant participant) {
//        SsafyMind ssafyMind = ssafyMindRepository.findById(participant.getRoomId()).get();
//        Team team = ssafyMind.getTeams().get(participant.getTeamNo());
//        if (!team.getMembers().contains(participant))
//            team.getMembers().add(participant);
//
//        // 변경 완료
//        return ssafyMindRepository.save(ssafyMind);
//    }
//
//    // 2. 게임방 퇴장 메서드
//    public synchronized SsafyMind exit(Participant participant) {
//        SsafyMind ssafyMind = ssafyMindRepository.findById(participant.getRoomId()).get();
//
//        // 방장이라면 방 자체를 삭제 후 종료
//        if (ssafyMind.getHost().equals(participant.getParticipantId())) {
//            ssafyMindRepository.deleteById(participant.getRoomId());
//            return null;
//        }
//        // 방장이 아니라면 유저 정보만 삭제
//        Team team = ssafyMind.getTeams().get(participant.getTeamNo());
//        team.getMembers().remove(participant);
//
//        // 변경 완료
//        return ssafyMindRepository.save(ssafyMind);
//    }
//
//    // 3. 그림 데이터 교환
//    public void draw(String roomId, Point point) {
//        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
//
//        // 그림 데이터 추가
//        ssafyMind.getPoints().add(point);
//        ssafyMindRepository.save(ssafyMind);
//    }
//
//    // 4. 정답 여부 확인
//    public synchronized MindMessage answer(String roomId, MindMessage mindMessage) {
//        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
//
//        // 정답 확인, 리스트의 마지막 부분 부터 문제 회수
//        int lastIndex = ssafyMind.getQuizzes().size() - 1;
//        if(ssafyMind.getQuizzes().get(lastIndex).getAnswer().equals(mindMessage.getMessage())) {
//            // 현재 문제의 상태를 바꾸고
//            mindMessage.setCorrect(true);
//            // 메세지 스택에 저장
//            ssafyMind.getChat().add(mindMessage);
//            ssafyMindRepository.save(ssafyMind);
//            // 메세지 반환
//            return mindMessage;
//        }
//        // 오답
//        ssafyMind.getChat().add(mindMessage);
//        ssafyMindRepository.save(ssafyMind);
//        return mindMessage;
//    }
//
//    // 5. 방을 순수하게 읽어오는 로직
//    public SsafyMind read(String roomId) {
//        return ssafyMindRepository.findById(roomId).get();
//    }
//
//    // 6. 1초마다 시간을 갱신하는 로직
//    public synchronized boolean time(String roomId, int cnt) {
//        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
//        if(ssafyMind.getTimeFlag().equals("stop"))
//            return false;
//        ssafyMind.setCurTime(cnt);
//        ssafyMindRepository.save(ssafyMind);
//        return true;
//    }
//
//    // 6 - 1. 시간 정지
//    public synchronized void timeStop(String roomId, String flag){
//        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
//        ssafyMind.setTimeFlag(flag);
//        ssafyMindRepository.save(ssafyMind);
//    }
//
//    // 7. 플레이어 변경
//    public synchronized int changePlayer(String roomId) {
//        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
//        // 현재 팀의 인원 수
//        int teamSize = ssafyMind.getTeams().get(ssafyMind.getTeamOrder().get(0)).getMembers().size();
//        // 현재 플레이어 번호
//        int curPlayer = ssafyMind.getCurPlayer();
//        // 다음 플레이어 번호
//        curPlayer = (curPlayer + 1) % teamSize;
//        // 세팅
//        ssafyMind.setCurPlayer(curPlayer);
//        // 저장
//        ssafyMindRepository.save(ssafyMind);
//        // 번호 전송
//        return curPlayer;
//    }
//
//    // 8. 다음 문제로 이동, 동시에 플레이어 순서 및 팀 순서 변경
//    public synchronized SsafyMind nextProblem(String roomId) {
//        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
//        int lastIndex = ssafyMind.getQuizzes().size() - 1;
//        // 마지막 문제를 제거하고
//        ssafyMind.getQuizzes().remove(lastIndex);
//        // 다음 팀으로 옮기고
//        ssafyMind.getTeamOrder().remove(0);
//        // 해당 팀의 첫 사람을 가리키는 인덱스 초기화
//        ssafyMind.setCurPlayer(0);
//        // 저장
//        return ssafyMindRepository.save(ssafyMind);
//    }
//}
