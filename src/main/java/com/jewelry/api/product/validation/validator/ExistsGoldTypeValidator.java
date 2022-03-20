package com.jewelry.api.product.validation.validator;

import com.jewelry.api.product.entity.GoldType;
import com.jewelry.api.product.repository.GoldTypeRepository;
import com.jewelry.api.product.validation.annotation.ExistsGoldType;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ExistsGoldTypeValidator implements ConstraintValidator<ExistsGoldType, Double> {

    @Autowired
    private GoldTypeRepository goldTypeRepository;

    private String message;

    @Override
    public void initialize(ExistsGoldType constraintAnnotation) {
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Double percentage, ConstraintValidatorContext context) {
        Optional<GoldType> goldTypeOpt = goldTypeRepository.findByPercentage(percentage);
        if(goldTypeOpt.isPresent()){
            return true;
        }

        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();

        return false;
    }
}
