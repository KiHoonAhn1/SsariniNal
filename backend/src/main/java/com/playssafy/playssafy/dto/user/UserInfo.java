package com.playssafy.playssafy.dto.user;

import com.playssafy.playssafy.domain.user.UserEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter // Lombok, getter/setter 자동화
@Getter
@ApiModel(value = "유저 정보", description = "회원 아이디, 이름, 비밀번호") // Swagger 모델 설명
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {
    @ApiModelProperty(value = "사용자 아이디", required = true)
    private String userId;
    @ApiModelProperty(value = "사용자 이름", required = true)
    private String userName;
    @ApiModelProperty(value = "비밀번호", required = true)
    private String userPass;

    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .userId(userId).userName(userName)
                .userPass(userPass).build();
        return userEntity;
    }
}
