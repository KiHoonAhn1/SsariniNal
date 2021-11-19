package com.playssafy.playssafy.service;

import com.playssafy.playssafy.dto.ssafymind.*;
import com.playssafy.playssafy.dto.waitroom.InitGame;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.dto.waitroom.Quiz;
import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import com.playssafy.playssafy.repository.SsafyMindRepository;
import com.playssafy.playssafy.repository.WaitRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SsafyMindService {
    @Autowired
    private SsafyMindRepository ssafyMindRepository;
    @Autowired
    private WaitRoomRepository waitRoomRepository;

    // 0. 게임방 생성 메서드
    public void createSsafyMind(InitGame initGame) {
        if(!ssafyMindRepository.findById(initGame.getRoomId()).isEmpty())
            return;
        SsafyMind ssafyMind = new SsafyMind();

        // 방 ID 초기화
        ssafyMind.setRoomId(initGame.getRoomId());
        // 방장 정보 추가
        ssafyMind.setHost(initGame.getHost());

        // 팀 진행 순서 초기화
        ssafyMind.setCurTeam(initGame.getExist());
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
        // 현재 팀 개수 초기화
        ssafyMind.setCurTeamCnt(teams.size());

        // 테스트 문제 리스트 ////////
        for(int i = 0; i < 3; i++) {
            ssafyMind.getQuizzes().add(new Quiz(1, "개발", "개발"));
        }
        for(int i = 0; i < 10; i++) {
            ssafyMind.getQuizzes().add(new Quiz(1, "싸피", "싸피"));
            ssafyMind.getQuizzes().add(new Quiz(1, "도커", "도커"));
            ssafyMind.getQuizzes().add(new Quiz(1, "프로님", "프로님"));
            ssafyMind.getQuizzes().add(new Quiz(1, "5기", "5기"));
            ssafyMind.getQuizzes().add(new Quiz(1, "삼성", "삼성"));
            ssafyMind.getQuizzes().add(new Quiz(1, "해피하우스", "해피하우스"));
            ssafyMind.getQuizzes().add(new Quiz(1, "유튜브", "유튜브"));
            ssafyMind.getQuizzes().add(new Quiz(1, "포크레인", "포크레인"));
            ssafyMind.getQuizzes().add(new Quiz(1, "우거지국", "우거지국"));
            ssafyMind.getQuizzes().add(new Quiz(1, "삼국시대", "삼국시대"));
            ssafyMind.getQuizzes().add(new Quiz(1, "자바", "자바"));
            ssafyMind.getQuizzes().add(new Quiz(1, "아이유", "아이유"));
            ssafyMind.getQuizzes().add(new Quiz(1, "대한민국", "대한민국"));
            ssafyMind.getQuizzes().add(new Quiz(1, "코로나", "코로나"));
            ssafyMind.getQuizzes().add(new Quiz(1, "오징어게임", "오징어게임"));
        }

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
        if(ssafyMindRepository.findById(participant.getRoomId()).isEmpty())
            return null;
        SsafyMind ssafyMind = ssafyMindRepository.findById(participant.getRoomId()).get();

        // 방장이라면 방 자체를 삭제 후 종료
        if (ssafyMind.getHost().equals(participant.getParticipantId())) {
            ssafyMindRepository.deleteById(participant.getRoomId());
            // 게임이 진행중이었던 경우 게임 진행 여부 초기화
            WaitRoom waitRoom = waitRoomRepository.findById(participant.getRoomId()).get();
            if(waitRoom.isProgress()) {
                waitRoom.setProgress(false);
                waitRoomRepository.save(waitRoom);
            }
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
    public synchronized MindMessage answer(String roomId, MindMessage mindMessage) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();

        // 정답 확인, 리스트의 마지막 부분 부터 문제 회수
        int lastIndex = ssafyMind.getQuizzes().size() - 1;
        if(ssafyMind.getQuizzes().get(lastIndex).getAnswer().equals(mindMessage.getMessage())) {
            // 현재 문제의 상태를 바꾸고
            mindMessage.setCorrect(true);
            // 해당 팀의 점수 높이기
            ssafyMind.getScores()[mindMessage.getTeamNo()] += 100;
            // 메세지 스택에 저장
            ssafyMind.getChat().add(mindMessage);
            ssafyMindRepository.save(ssafyMind);
            // 메세지 반환
            return mindMessage;
        }
        // 오답
        ssafyMind.getChat().add(mindMessage);
        ssafyMindRepository.save(ssafyMind);
        return mindMessage;
    }

    // 5. 1초마다 시간을 갱신하는 로직
    public synchronized boolean time(String roomId, int cnt) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
        if(ssafyMind.getTimeFlag().equals("stop"))
            return false;
        ssafyMind.setCurTime(cnt);
        ssafyMindRepository.save(ssafyMind);
        return true;
    }

    // 6. 시간 정지
    public synchronized void timeStop(String roomId, String flag){
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
        ssafyMind.setTimeFlag(flag);
        ssafyMindRepository.save(ssafyMind);
    }

    // 7. 플레이어 변경
    public synchronized int changePlayer(String roomId) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
        // 현재 팀의 인원 수
        int teamSize = ssafyMind.getTeams().get(ssafyMind.getTeamOrder().get(0)).getMembers().size();
        // 현재 플레이어 번호
        int curPlayer = ssafyMind.getCurPlayer();
        // 다음 플레이어 번호
        curPlayer = (curPlayer + 1) % teamSize;
        // 세팅
        ssafyMind.setCurPlayer(curPlayer);
        // 저장
        ssafyMindRepository.save(ssafyMind);
        // 번호 전송
        return curPlayer;
    }

    // 8. 다음 문제로 이동, 동시에 플레이어 순서 및 팀 순서 변경
    public synchronized SsafyMind nextProblem(String roomId) {
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
        int lastIndex = ssafyMind.getQuizzes().size() - 1;
        // 마지막 문제를 제거하고
        ssafyMind.getQuizzes().remove(lastIndex);
        // 다음 팀으로 옮기고
        ssafyMind.getTeamOrder().remove(0);
        // 만일, 팀 개수가 0개라면 null 넣어주기
        if(ssafyMind.getTeamOrder().size() == 0)
            ssafyMind.getTeamOrder().add(null);
        // 해당 팀의 첫 사람을 가리키는 인덱스 초기화
        ssafyMind.setCurPlayer(0);
        // 저장
        return ssafyMindRepository.save(ssafyMind);
    }

    // 9. 게임 종료
    public synchronized void end(String roomId) {
        // 기존 방의 점수 갱신
        WaitRoom waitRoom = waitRoomRepository.findById(roomId).get();
        SsafyMind ssafyMind = ssafyMindRepository.findById(roomId).get();
        for(int i = 1; i < ssafyMind.getScores().length; i++) {
            waitRoom.getScores()[i] += ssafyMind.getScores()[i];
        }
        // 저장
        waitRoomRepository.save(waitRoom);
    }
}
