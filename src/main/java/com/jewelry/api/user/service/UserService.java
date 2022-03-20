package com.jewelry.api.user.service;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.user.dto.UserChangePasswordDto;
import com.jewelry.api.user.dto.UserCreateDto;
import com.jewelry.api.user.dto.UserUpdateDto;
import com.jewelry.api.user.entity.User;
import com.jewelry.api.user.projection.UserInfo;

import java.util.Optional;

public interface UserService extends GenericService<User, Long> {
    User save(UserCreateDto dto);

    Optional<User> findByEmail(String email);

    Optional<UserInfo> findUserInfoByEmail(String email);

    void updateUserInfo(UserUpdateDto dto, String email);

    void updatePassword(String newPassword, String email);

}
