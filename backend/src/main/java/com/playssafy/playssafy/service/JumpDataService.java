//package com.playssafy.playssafy.service;
//
//import com.playssafy.playssafy.domain.Quiz.MindDto;
//import com.playssafy.playssafy.domain.Quiz.MindEntity;
//import com.playssafy.playssafy.dto.play.JumpGameData;
//import com.playssafy.playssafy.repository.JumpDataRepository;
//import com.playssafy.playssafy.repository.SpeakRepository;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class JumpDataService {
//    @Autowired
//    private JumpDataRepository jumpDataRepository;
//
//    //초기 데이터 세팅
//    public JumpGameData regist(String roomId, List<String> list) throws Exception{
//        Optional<JumpGameData> registeredJumpGameData = jumpDataRepository.findById(roomId);
//        if (registeredJumpGameData.isPresent()) {
//            System.out.println("이미 생성된 방입니다");
//            return null;
//        }
//        return registeredJumpGameData.save(new Room(roomCode,
//                roomName,
//                masterID,
//                new HashMap<Long,Team>(),
//                0,
//                new HashMap<Integer, Integer>()));
//    }
//
//    //게임 도중 데이터 입출력
//
//    //게임 종료
//
//}
