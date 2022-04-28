package com.jewelry.api.product.validation.annotation;

import com.jewelry.api.product.validation.validator.ExistsProductSkuValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistsProductSkuValidator.class)
public @interface ExistsProductSku {

    String message() default "{product.exists-product-sku}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
