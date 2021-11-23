package com.playssafy.playssafy.restcontroller;

import com.playssafy.playssafy.dto.waitroom.WaitRoom;
import com.playssafy.playssafy.repository.SpeakGameRepository;
import com.playssafy.playssafy.repository.SsafyMindRepository;
import com.playssafy.playssafy.repository.SsazipJumpRepository;
import com.playssafy.playssafy.repository.WaitRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/sign")
public class SignController {
    @Autowired
    private WaitRoomRepository waitRoomRepository;
    @Autowired
    private SsafyMindRepository ssafyMindRepository;
    @Autowired
    private SpeakGameRepository speakGameRepository;
    @Autowired
    private SsazipJumpRepository ssazipJumpRepository;

    @GetMapping(value = "/{roomId}")
    public void waitSign(@PathVariable String roomId) {
        WaitRoom waitRoom = waitRoomRepository.findById(roomId).get();
        waitRoom.setSign(waitRoom.getSign() + 1);
        waitRoomRepository.save(waitRoom);
    }
}
