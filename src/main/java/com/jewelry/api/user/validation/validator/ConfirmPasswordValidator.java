package com.jewelry.api.user.validation.validator;

import com.jewelry.api.user.validation.annotation.ConfirmPassword;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, Object> {

    private String message;
    private String getPassword;
    private String getRePassword;

    @Override
    public void initialize(ConfirmPassword constraintAnnotation) {
        this.message = constraintAnnotation.message();
        this.getPassword = constraintAnnotation.getPassword();
        this.getRePassword = constraintAnnotation.getRePassword();
    }

    @Override
    public boolean isValid(Object target, ConstraintValidatorContext context) {
        try {
            String password = (String) target.getClass().getMethod(getPassword).invoke(target);
            String confirmPassword = (String) target.getClass().getMethod(getRePassword).invoke(target);

            if (password.equals(confirmPassword))
                return true;

            context.buildConstraintViolationWithTemplate(message)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();

        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
                | SecurityException e) {
            e.printStackTrace();
        }

        return false;
    }
}