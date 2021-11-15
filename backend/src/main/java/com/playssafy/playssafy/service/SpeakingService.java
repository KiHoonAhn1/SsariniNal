package com.playssafy.playssafy.service;

import com.playssafy.playssafy.dto.speaking.SpeakMessage;
import com.playssafy.playssafy.dto.speaking.Speaking;
import com.playssafy.playssafy.dto.ssafymind.Quiz;
import com.playssafy.playssafy.dto.ssafymind.Team;
import com.playssafy.playssafy.dto.waitroom.InitGame;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.repository.SpeakGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpeakingService {
    @Autowired
    private SpeakGameRepository speakGameRepository;

    // 0. 게임방 생성 메서드
    public void createSpeaking(InitGame initGame) {
        if(!speakGameRepository.findById(initGame.getRoomId()).isEmpty())
            return;
        Speaking speaking = new Speaking();

        // 방 ID 초기화
        speaking.setRoomId(initGame.getRoomId());
        // 방장 정보 추가
        speaking.setHost(initGame.getHost());

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
                speaking.getTeamOrder().add(team);
            }
        }
        // 현재 팀 개수 초기화
        speaking.setCurTeamCnt(teams.size());

        // 테스트 문제 리스트 ////////
        for(int i = 0; i < 20; i++) {
            speaking.getQuizzes().add(new Quiz(1, "삼성 청년 소프트웨어 아카데미", "삼성청년소프트웨어아카데미"));
            speaking.getQuizzes().add(new Quiz(1, "간장공장 공장장", "간장공장공장장"));
            speaking.getQuizzes().add(new Quiz(1, "내가 그린 기린 그림", "내가그린기린그림"));
        }
        // 팀 초기화////////
        speaking.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "1", "김태현1", 1));
        speaking.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "2", "김태현2", 1));
        speaking.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "3", "김태현3", 1));
        speaking.getTeams().get(1).getMembers().add(new Participant(initGame.getRoomId(), "4", "김태현4", 1));
        speaking.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "1", "이장섭1", 2));
        speaking.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "2", "이장섭2", 2));
        speaking.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "3", "이장섭3", 2));
        speaking.getTeams().get(2).getMembers().add(new Participant(initGame.getRoomId(), "4", "이장섭4", 2));


        speakGameRepository.save(speaking);
    }

    // 1. 게임방 입장 메서드
    public synchronized Speaking enter(Participant participant) {
        Speaking speaking = speakGameRepository.findById(participant.getRoomId()).get();
        Team team = speaking.getTeams().get(participant.getTeamNo());
        if (!team.getMembers().contains(participant))
            team.getMembers().add(participant);

        // 변경 완료
        return speakGameRepository.save(speaking);
    }

    // 2. 게임방 퇴장 메서드
    public synchronized Speaking exit(Participant participant) {
        Speaking speaking = speakGameRepository.findById(participant.getRoomId()).get();

        // 방장이라면 방 자체를 삭제 후 종료
        if (speaking.getHost().equals(participant.getParticipantId())) {
            speakGameRepository.deleteById(participant.getRoomId());
            return null;
        }
        // 방장이 아니라면 유저 정보만 삭제
        Team team = speaking.getTeams().get(participant.getTeamNo());
        team.getMembers().remove(participant);

        // 변경 완료
        return speakGameRepository.save(speaking);
    }

    // 3. 정답 여부 확인
    public synchronized SpeakMessage answer(String roomId, SpeakMessage speakMessage) {
        Speaking speaking = speakGameRepository.findById(roomId).get();
        // 정답 여부 기본값을 fasle로 설정
        speakMessage.setCorrect(false);

        int lastIndex = speaking.getQuizzes().size() - 1;
        System.out.println(speakMessage.getMessage());
        System.out.println(speaking.getQuizzes().get(lastIndex).getAnswer().equals(speakMessage.getMessage().replaceAll(" ","")));
        if(speaking.getQuizzes().get(lastIndex).getAnswer().equals(speakMessage.getMessage().replaceAll(" ",""))) {
            System.out.println("here");
            // 정답으로 상태를 바꿔주고
            speakMessage.setCorrect(true);
            System.out.println(speakMessage.isCorrect());
            speakGameRepository.save(speaking);
            return speakMessage;
        }
        speakGameRepository.save(speaking);
        // 오답
        return speakMessage;
    }

    // 4. 플레이어 변경
    public synchronized int changePlayer(String roomId) {
        Speaking speaking = speakGameRepository.findById(roomId).get();
        // 현재 팀의 인원 수
        int teamSize = speaking.getTeams().get(speaking.getTeamOrder().get(0)).getMembers().size();
        // 현재 플레이어 번호
        int curPlayer = speaking.getCurPlayer();
        // 다음 플레이어 번호
        curPlayer = (curPlayer + 1) % teamSize;
        // 세팅
        speaking.setCurPlayer(curPlayer);
        // 저장
        speakGameRepository.save(speaking);
        // 번호 전송
        return curPlayer;
    }

    // 5. 다음 문제로 이동
    public synchronized Speaking nextProblem(String roomId) {
        Speaking speaking = speakGameRepository.findById(roomId).get();
        int lastIndex = speaking.getQuizzes().size() - 1;
        // 마지막 문제를 제거하고
        speaking.getQuizzes().remove(lastIndex);
        // 다음 팀으로 옮기고
        speaking.getTeamOrder().remove(0);
        // 해당 팀의 첫 사람을 가리키는 인덱스 초기화
        speaking.setCurPlayer(0);
        // 저장
        return speakGameRepository.save(speaking);
    }

    // 5.
}
