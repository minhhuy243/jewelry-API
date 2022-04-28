package com.jewelry.api.security.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.security.entity.RefreshToken;
import com.jewelry.api.security.exception.TokenRefreshException;
import com.jewelry.api.security.jwt.JwtUtil;
import com.jewelry.api.security.repository.RefreshTokenRepository;
import com.jewelry.api.security.service.itf.RefreshTokenService;
import com.jewelry.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class RefreshTokenServiceImpl extends GenericServiceImpl<RefreshToken, Long> implements RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public void updateRefreshToken(String oldRefreshToken, String newRefreshToken) {
        Optional<RefreshToken> refreshTokenEntity = refreshTokenRepository.findByToken(oldRefreshToken);
        if(refreshTokenEntity.isPresent()) {
            refreshTokenEntity.get().setToken(newRefreshToken);
            refreshTokenRepository.save(refreshTokenEntity.get());
        }
    }

    @Transactional
    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }
}
