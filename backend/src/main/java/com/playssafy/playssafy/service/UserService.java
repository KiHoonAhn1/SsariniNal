package com.playssafy.playssafy.service;

import com.playssafy.playssafy.domain.user.UserEntity;
import com.playssafy.playssafy.dto.user.UserInfo;
import com.playssafy.playssafy.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.swagger2.mappers.ModelMapper;

import javax.transaction.Transactional;

@Service // 서비스 선언
@AllArgsConstructor // lombok. 클래스에 존재하는 모든 필드에 대한 생성자 자동 생성
@NoArgsConstructor // Parameter가 없는 생성자 자동 생성
public class UserService {
    @Autowired // 자동 주입(설정한 인스턴스 및 메서드가 자동으로 호출된다)
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Transactional // 데이터베이스 트랜잭션 설정(자세한 내용은 데이터베이스 공부), 기본적으로 commit 및 rollback 처리 자동화.
    public void createUser(UserInfo request) {
        userRepository.save(request.toEntity());
    }

    @Transactional
    public boolean checkId(String userId){
        return userRepository.existsById(userId);
    }

    @Transactional
    public UserInfo loginUser(UserInfo request){
        UserEntity userEntity = userRepository.findByIdAndPass(request.getId(), request.getPass());
        if(userEntity == null)
            return null;
        UserInfo userInfo = new UserInfo(userEntity.getId(), userEntity.getName(), null);
        return userInfo;
    }
}
