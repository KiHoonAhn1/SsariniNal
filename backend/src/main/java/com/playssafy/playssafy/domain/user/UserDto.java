package com.playssafy.playssafy.domain.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter // Lombok, getter/setter 자동화
@Getter
@ApiModel // Swagger 모델 설명
@NoArgsConstructor
public class UserDto {
    @ApiParam(value = "사용자 아이디", required = true, example = "ssafy")
    private String userId;
    @ApiParam(value = "사용자 이름", required = true, example = "홍길동")
    private String userName;
    @ApiParam(value = "비밀번호", required = true, example = "ssafy")
    private String userPass;

    // ???
    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .userId(userId).userName(userName)
                .userPass(userPass).build();
        return userEntity;
    }
}
