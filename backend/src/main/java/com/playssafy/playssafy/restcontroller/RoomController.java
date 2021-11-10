//package com.playssafy.playssafy.controller;
//
//import com.playssafy.playssafy.dto.play.Guest;
//import com.playssafy.playssafy.dto.play.Room;
//import com.playssafy.playssafy.dto.play.Team;
//import com.playssafy.playssafy.service.RoomService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//
//@Api(tags={"3.Room info"})
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/room")
//public class RoomController {
//    @Autowired
//    private RoomService roomService;
//
//    @ApiOperation(value = "방 생성")
//    @PostMapping("/register")
//    @ResponseBody
//    public ResponseEntity<Room> createRoom(@RequestBody Room room,@RequestBody Team[] teams) throws Exception {
//        HashMap<Long, Team> guestMap = null;
//        for(int i=0;i<teams.length;i++){
//            guestMap.put(teams[i].getGuestId(),teams[i]);
//        }
//        room.setGuests(guestMap);
//        return new ResponseEntity<>(roomService.createRoom(room.getRoomCode(), room.getRoomName(), room.getMasterID()), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "방 정보 요청")
//    @GetMapping("/register/{roomCode}")
//    public ResponseEntity<Room> getRoom(@PathVariable Long roomCode) throws Exception {
//        return new ResponseEntity<>(roomService.getRoom(roomCode).orElse(null), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "방 삭제 요청")
//    @DeleteMapping("/delete/{roomCode}")
//    public ResponseEntity<Boolean> deleteRoom(@PathVariable Long roomCode) throws Exception {
//        return new ResponseEntity<>(roomService.deleteRoom(roomCode), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "게스트 생성, 이동")
//    @PostMapping("/guest")
//    @ResponseBody
//    public ResponseEntity<Room> updateGuest(@RequestBody Guest guest) throws Exception {
//        return new ResponseEntity<>(roomService.updateGuest(guest), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "게스트 삭제")
//    @DeleteMapping("/guest")
//    @ResponseBody
//    public ResponseEntity<Room> leaveGuest(@RequestBody Guest guest) throws Exception {
//        return new ResponseEntity<>(roomService.leaveGuest(guest), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "방 상태 변경")
//    @GetMapping("/roomstate/{roomCode}/{gameState}")
//    @ResponseBody
//    public ResponseEntity<Room> updateRoomState(@PathVariable Long roomCode, int gameState) throws Exception {
//        return new ResponseEntity<>(roomService.updateRoomState(roomCode,gameState), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "점수 변경")
//    @PostMapping("/roomScore")
//    @ResponseBody
//    public ResponseEntity<Room> updateRoomScore(@RequestBody Room room) throws Exception {
//        Long roomCcde= room.getRoomCode();
//        HashMap<Integer,Integer> score= room.getScores();
//        return new ResponseEntity<>(roomService.updateRoomScore(roomCcde,score), HttpStatus.OK);
//    }
//
//
//}
