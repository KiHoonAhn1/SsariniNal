package com.playssafy.playssafy.dto.play;

import io.swagger.annotations.ApiModel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.List;

@Getter
@Data
@Builder
@NoArgsConstructor
@RedisHash("jumpdata")
public class JumpGameData {
    @Id
    //@Id 어노테이션은 JPA와 동일한 역할을 수행합니다. "member:{id}"의 위치에 자동으로 generate 값이 들어갑니다.
    private String roomId;
    private HashMap<Integer, Integer> Awarded;//수상자, 등수-팀번호
    private List<Integer> list; //플레이 팀 리스트
    private List<Integer> nextList; //다음 강 리스트

    public JumpGameData(String roomId, HashMap<Integer, Integer> awarded, List<Integer> list, List<Integer> nextList) {
        this.roomId = roomId;
        Awarded = awarded;
        this.list = list;
        this.nextList = nextList;
    }
}
