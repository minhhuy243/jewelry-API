package com.jewelry.api.product.dto.category;

import com.jewelry.api.product.validation.annotation.UniqueCategoryCode;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CategoryCreateDto {

    @NotBlank(message = "{product-category.code.not-blank}")
    @Size(min = 3, max = 10, message = "{product-category.code.size}")
    @UniqueCategoryCode
    private String code;

    @NotBlank(message = "{product-category.name.not-blank}")
    @Size(min = 1, max = 30, message = "{product-category.name.size}")
    private String name;

}
