package com.playssafy.playssafy.domain.user;

import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name="user") // DB 테이블을 해당 Entity로 Mapping.
@NoArgsConstructor
@ApiModel
public class UserEntity {
    @Id // primary key
    private String userId;
    private String userName;
    private String userPass;

    @Builder
    public UserEntity(String userId, @NonNull String userName, String userPass){
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }
}
