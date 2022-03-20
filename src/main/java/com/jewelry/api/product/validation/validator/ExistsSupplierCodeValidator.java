package com.jewelry.api.product.validation.validator;

import com.jewelry.api.product.entity.Supplier;
import com.jewelry.api.product.repository.SupplierRepository;
import com.jewelry.api.product.validation.annotation.ExistsSupplierCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ExistsSupplierCodeValidator implements ConstraintValidator<ExistsSupplierCode, String> {

    @Autowired
    private SupplierRepository supplierRepository;

    private String message;

    @Override
    public void initialize(ExistsSupplierCode constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String supplierCode, ConstraintValidatorContext context) {
        Optional<Supplier> supplierOpt = supplierRepository.findByCode(supplierCode);
        if(supplierOpt.isPresent()){
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
