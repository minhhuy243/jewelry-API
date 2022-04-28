package com.jewelry.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jewelry.api.user.util.UserGender;
import com.jewelry.api.user.validation.annotation.ConfirmPassword;
import com.jewelry.api.user.validation.annotation.UniqueEmail;
import com.jewelry.api.user.validation.annotation.UniquePhoneNumber;
import com.jewelry.api.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
@ConfirmPassword()
public class UserCreateDto {

    @Email(message = "{user.email.format}")
    @UniqueEmail
    private String email;

    @NotBlank(message = "{user.password.not-blank}")
    @Size(min = 8, max = 30, message = "{user.password.size}")
    private String password;

    @NotBlank
    private String rePassword;

    @NotBlank(message = "{user.full-name.not-blank}")
    @Size(min = 3, max = 50, message = "{user.full-name.size}")
    private String fullName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.BIRTHDAY_FORMAT)
    private LocalDate birthday;

    @NotBlank(message = "{user.phone-number.not-blank}")
    @Pattern(regexp = "(^((?=(0))[0-9]{10})$)", message = "{user.phone-number.pattern}")
    @UniquePhoneNumber
    private String phoneNumber;

    @NotBlank(message = "{user.address.not-blank}")
    @Size(min = 20, max = 100, message = "{user.address.size}")
    private String address;

}