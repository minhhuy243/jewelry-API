package com.jewelry.api.shopping.validation.annotation;

import com.jewelry.api.shopping.validation.validator.ConfirmCartItemUpdateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = ConfirmCartItemUpdateValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfirmCartItemUpdate {

    String getProductSku() default "getProductSku";

    String getQuantity() default "getQuantity";

    String messageProductSku() default "{product.exists}";

    String messageQuantity() default "{product.is-stock}";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
