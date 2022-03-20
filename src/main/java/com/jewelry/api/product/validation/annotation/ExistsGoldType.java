package com.jewelry.api.product.validation.annotation;

import com.jewelry.api.product.validation.validator.ExistsGoldTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ExistsGoldTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistsGoldType {

    String message() default "{product.exists-gold-type}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
