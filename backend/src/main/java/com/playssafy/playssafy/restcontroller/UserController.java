package com.playssafy.playssafy.restcontroller;

import com.playssafy.playssafy.dto.user.UserInfo;
import com.playssafy.playssafy.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"1.User"})
@RestController // REST API를 사용한다는 것을 알려준다.
@RequestMapping("/user") // 요청의 접두사 설정
public class UserController {
    @Autowired // 자동 주입
    private UserService userService;

    // 1. 회원 가입
    @ApiOperation(value = "회원 가입 요청", notes = "회원 가입을 요청한다") // Swagger API 이름 및 설명 설정
    @PostMapping(value = "/signup") // REST 타입(GET, POST, PUT, DELETE)
    public void createUser(@ApiParam(name = "userInfo", value = "사용자 정보") // 매개 변수 이름, 제목, 예시 설정
                           @RequestBody UserInfo request) throws Exception { // RequestBody를 사용하여 데이터 요청을 받는다(UserDto에 정의 되어 있음)
        userService.createUser(request);
    }

    // 2. 아이디 중복 확인
    @ApiOperation(value = "아이디 중복 확인 요청", notes = "아이디가 중복되는지 여부를 확인한다")
    @GetMapping(value = "/checkId")
    public boolean checkId(@ApiParam(name = "userId", value = "유저 아이디") @RequestParam("id") String id) throws Exception {
        return userService.checkId(id);
    }

    // 3. 로그인
    @ApiOperation(value = "로그인 요청", notes = "로그인을 요청한다")
    @PostMapping(value = "/login")
    public UserInfo loginUser(@ApiParam(name = "userInfo", value = "사용자 아이디, 비밀번호를 입력으로 받는다.")
                          @RequestBody UserInfo request) throws Exception {
        return userService.loginUser(request);
    }
}
