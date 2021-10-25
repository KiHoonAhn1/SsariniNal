package com.playssafy.playssafy.domain.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity(name="user") // DB 테이블을 해당 Entity로 Mapping.
@NoArgsConstructor
@ApiModel
public class UserEntity {
    @Id // primary key
    @ApiModelProperty(value="아이디") // ???
    @ApiParam(value = "사용자 아이디", required = true, example = "ssafy")
    private String userId;
    @Column(name="user_name") // Column과 Mapping.
    @NonNull // Not Null.
    @ApiModelProperty(required = true, value = "이름")
    private String userName;
    @ApiModelProperty(required = true, value = "비밀번호")
    private String userPass;

    @Builder
    public UserEntity(String userId, @NonNull String userName, String userPass){
        this.userId = userId;
        this.userName = userName;
        this.userPass = userPass;
    }
}
