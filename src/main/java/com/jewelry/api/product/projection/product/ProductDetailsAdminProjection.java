package com.jewelry.api.product.projection.product;

import com.jewelry.api.product.entity.Image;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public interface ProductDetailsAdminProjection {

    String getSku();
    String getName();
    Double getPrice();
    String getAvatar();
    String getDescription();
    Double getGoldWeight();
    Integer getQuantity();
    Double getGoldTypePercentage();
    Double getCostPrice();
    String getSupplierCode();
    String getCategoryCode();

//    Optional<Set<Image>> getImages();
//    default Set<String> getImagesGDriveId() {
//        return getImages().get().stream().map(image -> image.getGDriveId()).collect(Collectors.toSet());
//    };
}
