package com.jewelry.api.user.controller;

import com.jewelry.api.security.dto.UserDetailsDto;
import com.jewelry.api.user.dto.UserChangePasswordDto;
import com.jewelry.api.user.dto.UserCreateDto;
import com.jewelry.api.user.dto.UserUpdateDto;
import com.jewelry.api.user.entity.User;
import com.jewelry.api.user.projection.UserInfo;
import com.jewelry.api.user.service.UserService;
import com.jewelry.api.util.ErrorUtils;
import com.jewelry.api.util.PasswordUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<Object> getCurrentUser(@AuthenticationPrincipal UserDetailsDto curUser){
        Optional<UserInfo> userOpt = userService.findUserInfoByEmail(curUser.getUsername());
        return ResponseEntity.ok(userOpt.get());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> addCustomer(@Valid @RequestBody UserCreateDto dto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.getErrorMessages(bindingResult));
        }
        userService.save(dto);
        return ResponseEntity.ok("Successful Register!");
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateInfo(@AuthenticationPrincipal UserDetailsDto curUser,
                                             @Valid @RequestBody UserUpdateDto dto,
                                             BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.getErrorMessages(bindingResult));
        }

        userService.updateUserInfo(dto, curUser.getUsername());
        return ResponseEntity.ok().body("Successful Update!");
    }

    @PutMapping("/update/change-password")
    public ResponseEntity<Object> changePassword(@AuthenticationPrincipal UserDetailsDto curUser,
                                                 @Valid @RequestBody UserChangePasswordDto dto,
                                                 BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.getErrorMessages(bindingResult));
        }

        userService.updatePassword(dto.getNewPassword(), curUser.getUsername());
        return ResponseEntity.ok().body("Successful Update!");
    }

}
