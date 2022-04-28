package com.jewelry.api.user.dto;

import com.jewelry.api.user.validation.annotation.ConfirmPassword;
import com.jewelry.api.user.validation.annotation.MatchOldPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@ConfirmPassword(getPassword = "getNewPassword", getRePassword = "getReNewPassword")
public class UserChangePasswordDto {

    @MatchOldPassword
    @NotBlank(message = "{user.password.not-blank}")
    @Size(min = 8, max = 30, message = "{user.password.size}")
    private String oldPassword;

    @NotBlank(message = "{user.password.not-blank}")
    @Size(min = 8, max = 30, message = "{user.password.size}")
    private String newPassword;

    @NotBlank(message = "{user.password.not-blank}")
    @Size(min = 8, max = 30, message = "{user.password.size}")
    private String reNewPassword;
}
