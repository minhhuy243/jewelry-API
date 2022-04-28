package com.jewelry.api.product.validation.validator;

import com.jewelry.api.product.entity.Product;
import com.jewelry.api.product.repository.ProductRepository;
import com.jewelry.api.product.validation.annotation.ExistsProductSku;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ExistsProductSkuValidator implements ConstraintValidator<ExistsProductSku, String> {

    @Autowired
    private ProductRepository productRepository;

    private String message;

    @Override
    public void initialize(ExistsProductSku constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String sku, ConstraintValidatorContext context) {
        Optional<Product> product = productRepository.findBySku(sku);
        if(product.isPresent()) {
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
        return false;
    }
}
