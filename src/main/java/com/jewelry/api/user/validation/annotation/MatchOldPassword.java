package com.jewelry.api.user.validation.annotation;

import com.jewelry.api.user.validation.validator.MatchOldPasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MatchOldPasswordValidator.class)
public @interface MatchOldPassword {

    String message() default "{user.match-old-password}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
