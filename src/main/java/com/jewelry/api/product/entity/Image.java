package com.jewelry.api.product.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jewelry.api.commondata.model.AbstractEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jewelry_product_images")
public class Image extends AbstractEntity {

    @NonNull
    @NotBlank(message = "{product-image.g-drive-id.not-blank}")
    private String gDriveId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;
}
