package com.jewelry.api.security.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RefreshTokenResponse {
    private String accessToken;
}
