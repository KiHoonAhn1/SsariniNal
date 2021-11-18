package com.playssafy.playssafy.service;

import com.playssafy.playssafy.dto.ssafymind.Team;
import com.playssafy.playssafy.dto.ssazipjump.SsazipJump;
import com.playssafy.playssafy.dto.waitroom.InitGame;
import com.playssafy.playssafy.dto.waitroom.Participant;
import com.playssafy.playssafy.repository.SsazipJumpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SsazipJumpService {
    @Autowired
    private SsazipJumpRepository ssazipJumpRepository;

    // rest 게임방 생성 메서드
    public synchronized void createSsazipJump(InitGame initGame) {
        if(!ssazipJumpRepository.findById(initGame.getRoomId()).isEmpty()){
            ssazipJumpRepository.deleteById(initGame.getRoomId());
        }
        SsazipJump ssazipJump = new SsazipJump();

        // 방 ID 초기화
        ssazipJump.setRoomId(initGame.getRoomId());
        // 방장 정보 추가
        ssazipJump.setHost(initGame.getHost());

        //게임 스코어 초기화
        ssazipJump.setGameScore1(0);
        ssazipJump.setGameScore2(0);

        ssazipJump.setGuideModalFlag(true);

        // 팀 진행 순서 초기화
        boolean[] exist = initGame.getExist();
        // 현재 팀이 존재한다면, 팀 넣기
        List<Integer> teamsNum = new ArrayList<>();
        for (int i = 1; i < exist.length; i++) {
            if (exist[i])
                teamsNum.add(i);
        }
        System.out.println("teamsNum.size="+teamsNum.size());
        for(int i=0;i<teamsNum.size();i++){
            System.out.println(teamsNum.get(i));
        }
        System.out.println("입장한 팀의 사이즈는 "+teamsNum.size());

        // 팀 섞기(100번)
        for (int i = 0; i < 100; i++) {
            // 임의의 두 인덱스를 지정하고
            int a = (int) (Math.random() * teamsNum.size());
            int b = (int) (Math.random() * teamsNum.size());
            // 교환
            int temp = teamsNum.get(a);
            teamsNum.set(a, teamsNum.get(b));
            teamsNum.set(b, temp);
        }
        System.out.println("섞은 뒤 사이즈는 "+teamsNum.size());

        // 최종 결정된 순서 넣기
//        for(int i = 0; i < teamsNum.size(); i++) {
            for (Integer team : teamsNum) {
                ssazipJump.getTeamOrder().add(team);
            }
//        }
        ssazipJump.setRemainRound(teamsNum.size());
        // 홀수 팀 수 상황 추가
        if(teamsNum.size()%2==1){
            ssazipJump.getTeamOrder().add(20);
        }

        //현재 강(라운드 표시)
        ssazipJump.setRemainRound(ssazipJump.getTeamOrder().size());
        int nrr=ssazipJump.getRemainRound()/2;
        System.out.println("nrr "+nrr);
        if(nrr==1) {
            nrr=0;
        }
        else if(nrr%2==1){
            nrr++;
        }
        System.out.println("nrr "+nrr);

        ssazipJump.setNextRemainRound(nrr);

        //시작 팀 번호 값 세팅
        ssazipJump.setTeamIdx1(0);
        ssazipJump.setTeamIdx2(1);

        //프리젠트 세팅
        boolean[]arr=new boolean[6];
        ssazipJump.setBeUserPresent1(arr);
        ssazipJump.setBeUserPresent2(arr);
        ssazipJump.setNowRoundNum(1);

        ssazipJump.setTeamOrder(teamsNum);
        System.out.println("방 초기 세팅값");
        System.out.println(ssazipJump.toString());
        ssazipJumpRepository.save(ssazipJump);
    }
    // 13,0, 방을 순수하게 읽어오는 로직
    public synchronized SsazipJump read(String roomId) {
        SsazipJump ssazipJump =ssazipJumpRepository.findById(roomId).orElse(null);
        if(ssazipJump==null) {
            ssazipJump.setType(400);//에러
        }
        return ssazipJump;
    }
    //11. 유저(방장을 제외한 모든 참여자) 등록 및 송출
    public synchronized SsazipJump regi(SsazipJump gotSsazipJump) {
        System.out.println("1. 유저(방장을 제외한 모든 참여자) 등록 및 송출 service");
        System.out.println("참가자="+gotSsazipJump.getParticipantName());
        System.out.println(gotSsazipJump.toString());
        SsazipJump ssazipJump = ssazipJumpRepository.findById(gotSsazipJump.getRoomId()).orElse(null);
        //맴버에 추가
        Team team = ssazipJump.getTeamsMember().get(gotSsazipJump.getTeamNo());
        Participant participant=new Participant(gotSsazipJump.getRoomId(),
                gotSsazipJump.getParticipantId(),
                gotSsazipJump.getParticipantName(),
                gotSsazipJump.getTeamNo(), false);
        if (!team.getMembers().contains(participant)){
            team.getMembers().add(participant);
        }
        ssazipJump.setType(gotSsazipJump.getType());
        // 최근 입장 유저 저장
        ssazipJump.setLastEntering(gotSsazipJump.getLastEntering());
        // 변경 완료
        return ssazipJumpRepository.save(ssazipJump);
    }
    // 12. 플레이어 유저 저장
    public synchronized SsazipJump regPlayer(SsazipJump gotSsazipJump) {
        SsazipJump ssazipJump = ssazipJumpRepository.findById(gotSsazipJump.getRoomId()).get();
        //중계할 데이터
        ssazipJump.setBeUserPresent1(gotSsazipJump.getBeUserPresent1());
        ssazipJump.setBeUserPresent2(gotSsazipJump.getBeUserPresent2());
//        //맴버에 추가
//        Team team = ssazipJump.getTeamsMember().get(gotSsazipJump.getTeamNo());
//        Participant participant=new Participant(gotSsazipJump.getRoomId(),
//                gotSsazipJump.getParticipantId(),
//                gotSsazipJump.getParticipantName(),
//                gotSsazipJump.getTeamNo());
//        if (!team.getMembers().contains(participant)){
//            team.getMembers().add(participant);
//        }
//        if(ssazipJump.getBeUserPresent1().length==0){
//            ssazipJump.setBeUserPresent1(gotSsazipJump.getBeUserPresent1());
//        }
//        if(ssazipJump.getBeUserPresent2().length==0){
//            ssazipJump.setBeUserPresent2(gotSsazipJump.getBeUserPresent2());
//        }
        ssazipJump.setType(gotSsazipJump.getType());
//        // 최근 입장 유저 저장
//        ssazipJump.setLastEntering(gotSsazipJump.getLastEntering());
        // 변경 완료
        return ssazipJumpRepository.save(ssazipJump);
    }


    // 1. 게임방 퇴장 메서드
    public synchronized SsazipJump exit(Participant participant) {
        SsazipJump ssazipJump = ssazipJumpRepository.findById(participant.getRoomId()).get();

        // 방장이라면 방 자체를 삭제 후 종료
        if (ssazipJump.getHost().equals(participant.getParticipantId())) {
            ssazipJumpRepository.deleteById(participant.getRoomId());
            return null;
        }
        // 방장이 아니라면 유저 정보만 삭제
        Team team = ssazipJump.getTeamsMember().get(participant.getTeamNo());
        team.getMembers().remove(participant);

        // 변경 완료
        return ssazipJumpRepository.save(ssazipJump);
    }

//    // 3. 대전 팀 변경
//    public synchronized int changeBattleTeam(String roomId) {
//        //return 1 = 동일 라운드 다음팀
//        //return 2 = 다음라운드 시작
//        //return 3 = 게임 종료
//
//        SsazipJump ssazipJump = ssazipJumpRepository.findById(roomId).get();
//        // 마지막 게임 여부 확인
//        if(ssazipJump.getTeamIdx2()==ssazipJump.getTeamOrder().size()-1){
//            //마지막 라운드 여부
//            if(ssazipJump.getRemainRound()==2){
//                return 3;
//            }
//            //다음라운드시
//            if(ssazipJump.getTeamOrderNext().size()%2==1) {
//                List<Integer> list=ssazipJump.getTeamOrderNext();
//                list.add(-2);
//            }
//                ssazipJump.setTeamOrder(ssazipJump.getTeamOrderNext());
//            ssazipJump.setTeamOrderNext(new ArrayList<>());
//            ssazipJump.setRemainRound(ssazipJump.getTeamOrderNext().size());
//            ssazipJump.setTeamIdx1(0);
//            ssazipJump.setTeamIdx2(1);
//            ssazipJumpRepository.save(ssazipJump);
//            return 2;
//        }
//        //같은라운드 다른 팀으로 변경시
//        else{
//            ssazipJump.setTeamIdx1(ssazipJump.getTeamIdx1()+2);
//            ssazipJump.setTeamIdx2(ssazipJump.getTeamIdx2()+2);
//            ssazipJumpRepository.save(ssazipJump);
//            return 1;
//        }
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
}
