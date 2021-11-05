package com.playssafy.playssafy.dto.play;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("guest")
//@RedisHash 어노테이션을 통해 설정한 값을 Redis의 key 값 prefix로 사용합니다.
public class Guest implements Serializable {

    private static final long serialVersionUID = 664865927712847110L;


    @Id
    //@Id 어노테이션은 JPA와 동일한 역할을 수행합니다. "member:{id}"의 위치에 자동으로 generate 값이 들어갑니다.
    private Long guestId;

    @Indexed
    private Long roomCode;
    private String guestName;
    private int teamNum;


    public Guest(Long roomCode, String guestName,int teamNum) {
        this.roomCode = roomCode;
        this.guestName = guestName;
        this.teamNum = teamNum;
    }
}
