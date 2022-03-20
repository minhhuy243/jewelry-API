package com.jewelry.api.shopping.entity;

import com.jewelry.api.commondata.model.AbstractEntity;
import com.jewelry.api.product.entity.Product;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jewelry_cart_items")
public class CartItem extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @NonNull
    @Min(value = 1)
    private Integer quantity;

}
