package com.jewelry.api.shopping.projection;

import java.time.LocalDateTime;

public interface CartItemWithQuantityProjection {

    LocalDateTime getCreatedAt();

    String getProductSku();
    String getProductName();
    Double getProductPrice();
    String getProductAvatar();
    String getProductSlug();
    Integer getProductQuantity();
    String getProductCategorySlug();

    Integer getQuantity();
}