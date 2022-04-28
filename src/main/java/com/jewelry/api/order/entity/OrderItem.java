package com.jewelry.api.order.entity;

import com.jewelry.api.commondata.model.AbstractEntity;
import com.jewelry.api.product.entity.Product;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@RequiredArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jewelry_order_item")
public class OrderItem extends AbstractEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Order order;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @NonNull
    private Double price;

    @NonNull
    private Integer quantity;
}
