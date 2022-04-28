package com.jewelry.api.security.validation.validator;

import com.jewelry.api.security.entity.RefreshToken;
import com.jewelry.api.security.repository.RefreshTokenRepository;
import com.jewelry.api.security.validation.annotation.ExistsRefreshToken;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ExistsRefreshTokenValidator implements ConstraintValidator<ExistsRefreshToken, String> {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    private String message;

    @Override
    public void initialize(ExistsRefreshToken constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String refreshToken, ConstraintValidatorContext context) {
        Optional<RefreshToken> refreshTokenOpt = refreshTokenRepository.findByToken(refreshToken);
        if(refreshTokenOpt.isPresent()) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
