package com.jewelry.api.user.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.role.entity.Role;
import com.jewelry.api.role.repository.RoleRepository;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.user.dto.UserChangePasswordDto;
import com.jewelry.api.user.dto.UserCreateDto;
import com.jewelry.api.user.dto.UserUpdateDto;
import com.jewelry.api.user.entity.User;
import com.jewelry.api.user.projection.UserInfo;
import com.jewelry.api.user.repository.UserRepository;
import com.jewelry.api.util.MapDtoToModel;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class UserServiceImpl extends GenericServiceImpl<User, Long> implements UserService {

    private final UserRepository userRepository;
    private final MapDtoToModel<Object, User> mapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User save(UserCreateDto dto) {
        User newUser = new User();
        newUser = mapper.map(dto, newUser);
        newUser.setEmail(dto.getEmail().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(dto.getPassword()));

        Set<Role> roles = new HashSet<>();
        Optional<Role> userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole.get());
        newUser.setRoles(roles);

        newUser.addCart(new Cart());

        return userRepository.save(newUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email, User.class);
    }

    @Override
    public Optional<UserInfo> findUserInfoByEmail(String email) {
        return userRepository.findByEmail(email, UserInfo.class);
    }

    @Transactional
    @Override
    public void updateUserInfo(UserUpdateDto dto, String email) {
        Optional<User> userUpdate = userRepository.findByEmail(email, User.class);
        userUpdate = Optional.ofNullable(mapper.map(dto, userUpdate.get()));
        userRepository.save(userUpdate.get());
    }

    @Transactional
    @Override
    public void updatePassword(String newPassword, String email) {
        Optional<User> userUpdate = userRepository.findByEmail(email, User.class);
        userUpdate.get().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userUpdate.get());
    }

}
