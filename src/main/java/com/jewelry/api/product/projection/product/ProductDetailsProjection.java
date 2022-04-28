package com.jewelry.api.product.projection.product;

public interface ProductDetailsProjection {

    String getSku();
    String getName();
    Double getPrice();
    String getAvatar();
    String getDescription();
    Double getGoldWeight();
    Integer getQuantity();

    String getCategoryName();
    Double getGoldTypePercentage();
//    List<String> getImagesGDriveId();

}
