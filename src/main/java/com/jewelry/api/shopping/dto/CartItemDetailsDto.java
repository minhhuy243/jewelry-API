package com.jewelry.api.shopping.dto;

import com.jewelry.api.product.dto.product.ShortProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CartItemDetailsDto {

    private ShortProductDto product;
    private Integer quantity;
}