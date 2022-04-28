package com.jewelry.api.security.dto;

import com.jewelry.api.security.validation.annotation.ExistsRefreshToken;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequest {

    @NotBlank
    @ExistsRefreshToken
    private String refreshToken;
}

