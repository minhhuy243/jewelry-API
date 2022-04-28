package com.jewelry.api.product.dto.goldtype;

import com.jewelry.api.product.validation.annotation.FormatPercentageGold;
import com.jewelry.api.product.validation.annotation.UniqueGoldType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoldTypeCreateDto {

    @FormatPercentageGold
    @UniqueGoldType
    private Double percentage;

}
