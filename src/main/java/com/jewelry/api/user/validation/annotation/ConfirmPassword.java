package com.jewelry.api.user.validation.annotation;

import com.jewelry.api.user.validation.validator.ConfirmPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ConfirmPassword {

    String getPassword() default "getPassword";

    String getRePassword() default "getRePassword";

    String message() default "{user.confirm-password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
