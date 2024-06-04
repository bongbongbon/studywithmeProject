package com.personal.project.repository;

import com.personal.project.entity.ReplyEntity;
import com.personal.project.entity.RereplyEntity;
import org.hibernate.query.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RereplyRepository extends JpaRepository<RereplyEntity, Long> {

    List<RereplyEntity> findByReplyId(Long replyId);

}
