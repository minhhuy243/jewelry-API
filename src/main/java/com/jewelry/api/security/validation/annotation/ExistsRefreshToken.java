package com.jewelry.api.security.validation.annotation;

import com.jewelry.api.security.validation.validator.ExistsRefreshTokenValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsRefreshTokenValidator.class)
public @interface ExistsRefreshToken {

    String message() default "{refreshToken.token.exists}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
