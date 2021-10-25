package com.playssafy.playssafy.controller;

import com.playssafy.playssafy.domain.user.UserDto;
import com.playssafy.playssafy.repository.UserRepository;
import com.playssafy.playssafy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"1.User"})
@RestController // REST API를 사용한다는 것을 알려준다.
@RequestMapping("/") // 요청의 접두사 설정
public class UserController {
    @Autowired // 자동 주입
    private UserService userService;
    
    // 1. 회원 가입
    @ApiOperation(value = "회원 가입 요청", notes = "회원 가입을 요청한다") // Swagger API 이름 및 설명 설정
    @PostMapping(value = "signup") // REST 타입(GET, POST, PUT, DELETE)
    public void createUser(@ApiParam(name = "user_info", value = "사용자 정보", example = "ssafy 김싸피 1234") // 매개 변수 이름, 제목, 예시 설정
                           @RequestBody UserDto request) throws Exception { // RequestBody를 사용하여 데이터 요청을 받는다(UserDto에 정의 되어 있음)
        userService.createUser(request);
    }

}
