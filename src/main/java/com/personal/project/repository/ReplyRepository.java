package com.personal.project.repository;

import com.personal.project.entity.ReplyEntity;
import com.personal.project.entity.StudyBoardEntity;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReplyRepository extends JpaRepository<ReplyEntity, Long> {

    Page<ReplyEntity> findAllByStudyBoardIdOrderByCreatedAtDesc(Long studyBoardId, PageRequest pageRequest);

    void deleteByIdAndUserId(Long replyId, Long userId);
}
