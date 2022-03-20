package com.jewelry.api.user.validation.validator;

import com.jewelry.api.security.dto.UserDetailsDto;
import com.jewelry.api.user.validation.annotation.MatchOldPassword;
import com.jewelry.api.util.PasswordUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchOldPasswordValidator implements ConstraintValidator<MatchOldPassword, String> {

    private String message;

    @Override
    public void initialize(MatchOldPassword constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String oldPasswordDto, ConstraintValidatorContext context) {
        UserDetailsDto userDetailsDto
                = (UserDetailsDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(PasswordUtils.isValidOldPassword(oldPasswordDto, userDetailsDto.getPassword())) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
