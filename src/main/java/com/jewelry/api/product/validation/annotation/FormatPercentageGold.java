package com.jewelry.api.product.validation.annotation;

import com.jewelry.api.product.validation.validator.FormatPercentageGoldValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FormatPercentageGoldValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FormatPercentageGold {

    String message() default "{product.format-percentage-gold}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
