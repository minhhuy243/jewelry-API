package com.jewelry.api.product.entity;

import com.jewelry.api.commondata.model.AbstractEntity;
import com.jewelry.api.order.entity.OrderItem;
import com.jewelry.api.shopping.entity.CartItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "jewelry_product")
public class Product extends AbstractEntity {

    @NotBlank(message = "{product.sku.not-blank}")
    @Size(min = 3, max = 10, message = "{product.sku.size}")
    @Column(unique = true)
    private String sku;

    @NotBlank(message = "{product.name.not-blank}")
    @Size(min = 5, max = 100, message = "{product.name.size}")
    private String name;

    @Size(max = 1000, message = "{product.description.size}")
    private String description;

    private Double goldWeight;
    private Double costPrice;
    private Double price;
    private Integer quantity;
    private String avatar;
    private String slug;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    @NotNull
    private Supplier supplier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @NotNull
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gold_type_id")
    @NotNull
    private GoldType goldType;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Image> images = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<CartItem> cartItems = new HashSet<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems = new HashSet<>();

    public void addImage(Image image) {
        this.images.add(image);
        image.setProduct(this);
    }

    public void removeImage(Image image) {
        this.images.remove(image);
        image.setProduct(null);
    }

}
