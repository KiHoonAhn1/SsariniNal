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
    private String id;
    @ApiModelProperty(value = "사용자 이름", required = true)
    private String name;
    @ApiModelProperty(value = "비밀번호", required = true)
    private String pass;

    public UserEntity toEntity(){
        UserEntity userEntity = UserEntity.builder()
                .id(id).name(name)
                .pass(pass).build();
        return userEntity;
    }
}
