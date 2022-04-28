package com.jewelry.api.product.dto.product;

import lombok.Data;

@Data
public class ProductSummaryDto {

    private Long id;
    private String sku;
    private String name;
    private String avatar;
    private Double price;
    private Double goldWeight;
    private Integer quantity;
    private String categoryName;
    private String supplierName;
    private Double goldTypePercentage;
}
