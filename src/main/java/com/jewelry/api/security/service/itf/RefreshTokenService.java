package com.jewelry.api.security.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.security.dto.RefreshTokenResponse;
import com.jewelry.api.security.entity.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService extends GenericService<RefreshToken, Long> {

    void updateRefreshToken(String oldRefreshToken, String newRefreshToken);

    void deleteByToken(String token);
}
