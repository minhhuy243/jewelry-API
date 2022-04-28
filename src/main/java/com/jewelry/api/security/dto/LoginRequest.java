package com.jewelry.api.security.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @Email(message = "{customer.email.format}")
    @NotBlank
    private String email;

    @NotBlank(message = "{customer.password.not-blank}")
    private String password;
}
