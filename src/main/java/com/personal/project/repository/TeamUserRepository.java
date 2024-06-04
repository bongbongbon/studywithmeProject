package com.personal.project.repository;

import com.personal.project.entity.TeamUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

@Repository
public interface TeamUserRepository extends JpaRepository<TeamUserEntity, Long> {


}
