package com.jewelry.api.product.projection.product;

public interface ShortProductProjection {

    String getSku();
    String getName();
    Double getPrice();
    String getAvatar();
    String getSlug();
    Integer getQuantity();
    String getCategorySlug();
}
