package com.personal.project.repository;

import com.personal.project.entity.QuestionEntity;
import com.personal.project.entity.ReplyEntity;
import com.personal.project.entity.TeamEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    Page<QuestionEntity> findAllByTeamIdOrderByCreatedAtDesc(Long teamId, PageRequest pageRequest);

}
