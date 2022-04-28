package com.jewelry.api.product.validation.validator;

import com.jewelry.api.product.repository.SupplierRepository;
import com.jewelry.api.product.validation.annotation.UniqueSupplierCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueSupplierCodeValidator implements ConstraintValidator<UniqueSupplierCode, String> {

    @Autowired
    private SupplierRepository supplierRepository;

    private String message;

    @Override
    public void initialize(UniqueSupplierCode constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String supplierCode, ConstraintValidatorContext context) {
        boolean isTakenSupplierCode = supplierRepository.countByCode(supplierCode) >= 1;
        if(!isTakenSupplierCode){
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}

