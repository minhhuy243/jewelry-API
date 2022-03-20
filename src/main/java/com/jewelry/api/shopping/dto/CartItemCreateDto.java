package com.jewelry.api.shopping.dto;

import com.jewelry.api.product.validation.annotation.ExistsProductSku;
import com.jewelry.api.shopping.validation.annotation.ConfirmCartItemCreate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@ConfirmCartItemCreate
public class CartItemCreateDto {

    @ExistsProductSku
    private String productSku;

    @Min(value = 1)
    private Integer quantity;
}
