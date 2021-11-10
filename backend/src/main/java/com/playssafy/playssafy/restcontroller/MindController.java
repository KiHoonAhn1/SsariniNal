//package com.playssafy.playssafy.controller;
//
//import com.playssafy.playssafy.dto.ssafymind.SsafyMind;
//import com.playssafy.playssafy.service.SsafyMindService;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@Api(tags={"4.mind game info"})
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/mind")
//public class MindController {
//    @Autowired
//    private SsafyMindService ssafyMindService;
//
//    @ApiOperation(value = "게임 생성")
//    @PostMapping("/register")
//    @ResponseBody
//    public ResponseEntity<SsafyMind> createGame(@RequestBody SsafyMind ssafyMind) throws Exception {
//        return new ResponseEntity<>(ssafyMindService.createGame(ssafyMind), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "게임 삭제 요청")
//    @DeleteMapping("/delete/{roomCode}")
//    public ResponseEntity<Boolean> deleteGame(@PathVariable Long roomCode) throws Exception {
//        return new ResponseEntity<>(ssafyMindService.deleteGame(roomCode), HttpStatus.OK);
//    }
//
//    @ApiOperation(value = "게임 진행 요청")
//    @GetMapping("/play")
//    public ResponseEntity<SsafyMind> playNextTeam(@PathVariable SsafyMind ssafyMind) throws Exception {
//        return new ResponseEntity<>(ssafyMindService.playNextTeam(ssafyMind), HttpStatus.OK);
//    }
//
//
//    @ApiOperation(value = "좌표 생성, 이동")
//    @PostMapping("/draw")
//    @ResponseBody
//    public ResponseEntity<SsafyMind> drawGirin(@RequestBody String roomCode,  String[] points) throws Exception {
//        return new ResponseEntity<>(ssafyMindService.drawGirin(roomCode,points), HttpStatus.OK);
//    }
//
//
//    @ApiOperation(value = "채팅")
//    @PostMapping("/chat")
//    @ResponseBody
//    public ResponseEntity<List<String[]>> chat(@RequestBody String roomCode, String guestId, String sentence) throws Exception {
//        return new ResponseEntity<>(ssafyMindService.chat(roomCode,guestId,sentence), HttpStatus.OK);
//    }
//
//}
