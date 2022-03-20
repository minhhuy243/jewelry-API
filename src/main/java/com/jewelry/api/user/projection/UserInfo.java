package com.jewelry.api.user.projection;

import com.jewelry.api.user.util.UserGender;

import java.time.LocalDate;

public interface UserInfo {
    String getEmail();
    String getFullName();
    UserGender getGender();
    LocalDate getBirthday();
    String getPhoneNumber();
    String getAddress();
}
