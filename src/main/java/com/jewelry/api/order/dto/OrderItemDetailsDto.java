package com.jewelry.api.order.dto;


import com.jewelry.api.product.dto.product.ShortProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class OrderItemDetailsDto {

    private ShortProductDto product;

    private Double price;

    private Integer quantity;
}
