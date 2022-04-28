package com.jewelry.api.product.dto.product;

import lombok.*;

import java.util.Set;

@Data
public class ProductDetailsAdminDto {

    private Long id;
    private String sku;
    private String name;
    private String avatar;
    private String description;
    private Double costPrice;
    private Double price;
    private Double goldWeight;
    private Integer quantity;
    private String supplierCode;
    private String categoryCode;
    private Double goldTypePercentage;
    private Set<String> images;
}
