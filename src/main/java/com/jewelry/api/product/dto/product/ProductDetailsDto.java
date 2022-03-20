package com.jewelry.api.product.dto.product;

import lombok.Data;

import java.util.Set;

@Data
public class ProductDetailsDto {

    private String sku;
    private String name;
    private String avatar;
    private String description;
    private Double price;
    private Double goldWeight;
    private Boolean inStock;

    private String categoryName;
    private Double goldTypePercentage;
    private Set<String> images;

}
