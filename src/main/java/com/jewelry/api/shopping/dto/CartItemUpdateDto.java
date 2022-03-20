package com.jewelry.api.shopping.dto;

import com.jewelry.api.product.validation.annotation.ExistsProductSku;
import com.jewelry.api.shopping.validation.annotation.ConfirmCartItemUpdate;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
@ConfirmCartItemUpdate
public class CartItemUpdateDto {

    @ExistsProductSku
    private String productSku;

    @Min(value = 1)
    private Integer quantity;
}
