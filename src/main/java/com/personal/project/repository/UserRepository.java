package com.personal.project.repository;

import com.personal.project.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 아이디가 존재하는지 확인하는 Jpa 구문
    Boolean existsByUsername(String username);

    Optional<UserEntity> findByUsername(String username);
}
