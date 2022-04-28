package com.jewelry.api.product.dto.product;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortProductDto {
    private String sku;
    private String name;
    private Double price;
    private String avatar;
    private String slug;
    private Boolean inStock;
    private String categorySlug;
}
