package com.jewelry.api.shopping.validation.validator;

import com.jewelry.api.shopping.dto.CartItemUpdateDto;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.shopping.entity.CartItem;
import com.jewelry.api.shopping.repository.CartRepository;
import com.jewelry.api.shopping.validation.annotation.ConfirmCartItemCreate;
import com.jewelry.api.shopping.validation.annotation.ConfirmCartItemUpdate;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class ConfirmCartItemUpdateValidator implements ConstraintValidator<ConfirmCartItemUpdate, Object> {

    @Autowired
    private CartRepository cartRepository;

    private String messageProductSku;
    private String messageQuantity;
    private String getProductSku;
    private String getQuantity;

    @Override
    public void initialize(ConfirmCartItemUpdate constraintAnnotation) {
        this.messageProductSku = constraintAnnotation.messageProductSku();
        this.messageQuantity = constraintAnnotation.messageQuantity();
        this.getProductSku = constraintAnnotation.getProductSku();
        this.getQuantity = constraintAnnotation.getQuantity();
    }

    @SneakyThrows
    @Override
    public boolean isValid(Object item, ConstraintValidatorContext context) {
        String productSku = (String)item.getClass().getMethod(getProductSku).invoke(item);
        Integer newQuantity = (Integer)item.getClass().getMethod(getQuantity).invoke(item);

        String email
                = SecurityContextHolder.getContext().getAuthentication().getName();
        Cart cart = cartRepository.findCartByUserEmail(email);
        Optional<CartItem> cartItemOpt
                = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getSku().equals(productSku)).findFirst();

        if(cartItemOpt.isPresent()) {
            if(newQuantity > cartItemOpt.get().getProduct().getQuantity()) {
                createConstraintViolation(context, messageQuantity);
                return false;
            }
        } else {
            createConstraintViolation(context, messageQuantity);
            return false;
        }
        return true;
    }

    public void createConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation()
                .disableDefaultConstraintViolation();
    }
}
