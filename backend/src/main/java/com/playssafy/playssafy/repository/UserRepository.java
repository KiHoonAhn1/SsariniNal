package com.playssafy.playssafy.repository;

import com.playssafy.playssafy.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // JPA 저장소 선언
public interface UserRepository extends JpaRepository<UserEntity, String> { // UserEntity 테이블에 접근하고, PK는 String.
    UserEntity findByIdAndPass(String id, String pass);
}
