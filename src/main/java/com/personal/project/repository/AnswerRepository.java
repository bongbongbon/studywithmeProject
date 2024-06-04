package com.personal.project.repository;

import com.personal.project.entity.AnswerEntity;
import com.personal.project.entity.RereplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    List<AnswerEntity> findByQuestionId(Long questionId);
}
