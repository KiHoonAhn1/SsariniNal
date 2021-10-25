package com.playssafy.playssafy.repository;

import com.playssafy.playssafy.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // JPA 저장소 선언
// ???
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
