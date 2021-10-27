package com.playssafy.playssafy.repository;

import com.playssafy.playssafy.domain.Quiz.MindEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MindRepository extends JpaRepository<MindEntity, Long> {
}