package com.jewelry.api.security.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.security.entity.RefreshToken;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends GenericRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
