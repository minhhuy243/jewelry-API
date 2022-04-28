package com.jewelry.api.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jewelry.api.user.util.UserGender;
import com.jewelry.api.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateDto {

    @NotBlank(message = "{user.fullName.NotBlank}")
    @Size(min = 3, max = 50, message = "{user.fullName.Size}")
    private String fullName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtils.BIRTHDAY_FORMAT)
    private LocalDate birthday;

    @NotBlank(message = "{user.address.NotBlank}")
    @Size(min = 20, max = 100, message = "{user.address.Size}")
    private String address;
}
