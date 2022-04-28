package com.jewelry.api.product.projection.product;

public interface ProductSummaryProjection {
    Long getId();
    String getSku();
    String getName();
    Double getPrice();
    String getAvatar();
    Double  getGoldWeight();
    Integer getQuantity();
    String  getSupplierName();
    String  getCategoryName();
    Double  getGoldTypePercentage();
}
