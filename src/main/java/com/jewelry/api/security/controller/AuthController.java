package com.jewelry.api.security.controller;

import com.jewelry.api.security.dto.LoginRequest;
import com.jewelry.api.security.dto.LoginResponse;
import com.jewelry.api.security.dto.RefreshTokenRequest;
import com.jewelry.api.security.dto.RefreshTokenResponse;
import com.jewelry.api.security.entity.RefreshToken;
import com.jewelry.api.security.jwt.JwtUtil;
import com.jewelry.api.security.service.itf.RefreshTokenService;
import com.jewelry.api.user.entity.User;
import com.jewelry.api.user.service.UserService;
import com.jewelry.api.util.ErrorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Object> authenticateUser(@RequestBody @Valid LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String accessToken = jwtUtil.generateJwtToken(authentication);
            String refreshToken = jwtUtil.generateJwtRefreshToken(authentication);
            Optional<User> user = userService.findByEmail(loginRequest.getEmail());
            if(user.get().getRefreshToken() != null) {
                user.get().getRefreshToken().setToken(refreshToken);
            } else {
                RefreshToken refreshTokenEntity = new RefreshToken(refreshToken, jwtUtil.getExpirationDate(refreshToken));
                user.get().addRefreshToken(refreshTokenEntity);
            }
            userService.save(user.get());

            Set<GrantedAuthority> authorities = new HashSet<>(authentication.getAuthorities());
            Set<String> roles = authorities.stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toSet());

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken, roles));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username or password is invalid!");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<Object> logout(@Valid @RequestBody RefreshTokenRequest refreshToken) {
        refreshTokenService.deleteByToken(refreshToken.getRefreshToken());
        return ResponseEntity.ok("Successful Logout!");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refreshJwtToken(@Valid @RequestBody RefreshTokenRequest refreshToken,
                                                  BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<String> errors = ErrorUtils.getErrorMessages(bindingResult);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errors);
        }
        String username
                = jwtUtil.getUsername(refreshToken.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        if(jwtUtil.isTokenExpired(refreshToken.getRefreshToken())) {
            String newRefreshToken = jwtUtil.generateJwtRefreshToken(authentication);
            refreshTokenService.updateRefreshToken(refreshToken.getRefreshToken(), newRefreshToken);
        }
        RefreshTokenResponse refreshTokenResponse = new RefreshTokenResponse(jwtUtil.generateJwtToken(authentication));
        return ResponseEntity.ok(refreshTokenResponse);
    }

}
