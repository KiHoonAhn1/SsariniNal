package com.playssafy.playssafy.service;

import com.playssafy.playssafy.domain.user.UserDto;
import com.playssafy.playssafy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service // 서비스 선언
@AllArgsConstructor // lombok. 클래스에 존재하는 모든 필드에 대한 생성자 자동 생성
@NoArgsConstructor // Parameter가 없는 생성자 자동 생성
public class UserService {
    @Autowired // 자동 주입, ???
    private UserRepository userRepository;

    @Transactional // ???
    public void createUser(UserDto request) {
        userRepository.save(request.toEntity());
    }
}
