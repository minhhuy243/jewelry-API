package com.jewelry.api.product.entity;

import com.jewelry.api.commondata.model.AbstractEntity;
import com.jewelry.api.product.validation.annotation.FormatPercentageGold;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "jewelry_product_gold_type")
public class GoldType extends AbstractEntity {

    @Column(unique = true)
    @NotNull
    @FormatPercentageGold
    private Double percentage;

    @OneToMany(mappedBy = "goldType", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();
}
