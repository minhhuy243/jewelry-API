package com.jewelry.api.user.validation.validator;

import com.jewelry.api.user.repository.UserRepository;
import com.jewelry.api.user.validation.annotation.UniquePhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniquePhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {

    @Autowired
    private UserRepository userRepository;

    private String message;

    @Override
    public void initialize(UniquePhoneNumber constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        boolean isTakenPhoneNumber = userRepository.countByPhoneNumber(phoneNumber) >= 1;
        if (!isTakenPhoneNumber) {
            return true;
        }
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
