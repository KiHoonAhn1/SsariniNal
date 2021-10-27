package com.playssafy.playssafy.repository;

import com.playssafy.playssafy.domain.Quiz.SpeakEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpeakRepository extends JpaRepository<SpeakEntity, Long> {
}
