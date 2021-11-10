//package com.playssafy.playssafy.service;
//
//import com.playssafy.playssafy.dto.play.Guest;
//import com.playssafy.playssafy.dto.play.Room;
//import com.playssafy.playssafy.dto.play.Team;
//import com.playssafy.playssafy.repository.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Optional;
//
//@Service
//public class RoomService {
//    @Autowired
//    private RoomRepository roomRepository;
//
//    //방 생성
//    public Room createRoom(Long roomCode, String roomName, String masterID) throws Exception{
//        Optional<Room> registeredRoom = roomRepository.findById(roomCode);
//        if (registeredRoom.isPresent()) {
//            System.out.println("이미 생성된 방입니다");
//            return null;
//        }
//        return roomRepository.save(new Room(roomCode,
//                roomName,
//                masterID,
//                new HashMap<Long,Team>(),
//                0,
//                new HashMap<Integer, Integer>()));
//    }
//
//    //방 정보 요청
//    public Optional<Room> getRoom(Long roomCode) throws Exception{
//        Optional<Room> roomInfo = roomRepository.findById(roomCode);
//        if (roomInfo.isPresent()) {
//            System.out.println(roomInfo.toString());
//        }
//        return roomInfo;
//    }
//
//    //방 삭제
//    public boolean deleteRoom(Long roomCode) throws Exception{
//        roomRepository.deleteById(roomCode);
//        Optional<Room> roomInfo = roomRepository.findById(roomCode);
//        if (roomInfo.isPresent()) {
//            return false;
//        }
//        else return true;
//    }
//
//    //게스트 이동
//    public Room updateGuest(Guest guest) throws Exception{
//        Optional<Room> roomInfo = roomRepository.findById(guest.getRoomCode());
//        Team updatedTeam=new Team(guest.getGuestId(),guest.getGuestName(), guest.getTeamNum());
//        HashMap<Long, Team> members= roomInfo.get().getGuests();//게스트, 게스트팀정보
//        members.put(guest.getGuestId(),updatedTeam);
//        return roomRepository.save(new Room(roomInfo.get().getRoomCode(),
//                roomInfo.get().getRoomName(),
//                roomInfo.get().getMasterID(),
//                members,
//                roomInfo.get().getGameState(),
//                roomInfo.get().getScores()));
//    }
//
//    //유저 퇴장
//    public Room leaveGuest(Guest guest) throws Exception {
//        Optional<Room> roomInfo = roomRepository.findById(guest.getRoomCode());
////        Team updatedTeam=new Team(guest.getGuestName(), guest.getTeamNum());
//        HashMap<Long, Team> members = roomInfo.get().getGuests();//게스트, 게스트팀정보
//        members.remove(guest.getGuestId());
//        return roomRepository.save(new Room(roomInfo.get().getRoomCode(),
//                roomInfo.get().getRoomName(),
//                roomInfo.get().getMasterID(),
//                members,
//                roomInfo.get().getGameState(),
//                roomInfo.get().getScores()));
//    }
//
//    //방의 현재 상태 업데이트
//    public Room updateRoomState(Long roomCode, int gameState) throws Exception {
//        Optional<Room> roomInfo = roomRepository.findById(roomCode);
//        return roomRepository.save(new Room(roomInfo.get().getRoomCode(),
//                roomInfo.get().getRoomName(),
//                roomInfo.get().getMasterID(),
//                roomInfo.get().getGuests(),
//                gameState,
//                roomInfo.get().getScores()));
//    }
//
//    //점수 업데이트
//    public Room updateRoomScore(Long roomCode, HashMap<Integer,Integer>score) throws Exception {
//        Optional<Room> roomInfo = roomRepository.findById(roomCode);
//        return roomRepository.save(new Room(roomInfo.get().getRoomCode(),
//                roomInfo.get().getRoomName(),
//                roomInfo.get().getMasterID(),
//                roomInfo.get().getGuests(),
//                roomInfo.get().getGameState(),
//                score));
//    }
//
//}
