package com.personal.project.repository;

import com.personal.project.entity.StudyBoardEntity;
import com.personal.project.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface StudyBoardRepository extends JpaRepository<StudyBoardEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "update StudyBoardEntity s \n" +
            "set s.view = s.view + 1 \n" +
            "where s.id = :id")
    int updateView(@Param("id") Long id);

    Page<StudyBoardEntity> findAll(Pageable pageable);

    void deleteByIdAndUser(Long id, UserEntity user);

    Optional<StudyBoardEntity> findByIdAndUserId(Long id, Long userId);
}
