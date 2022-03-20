package com.jewelry.api.product.validation.annotation;

import com.jewelry.api.product.validation.validator.UniqueCategoryCodeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueCategoryCodeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCategoryCode {

    String message() default "{product.unique-category-code}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
