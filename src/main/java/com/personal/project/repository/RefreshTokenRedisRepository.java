package com.personal.project.repository;

import com.personal.project.entity.RefreshToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {

    @Transactional
    void deleteByRefresh(String refresh);
}
