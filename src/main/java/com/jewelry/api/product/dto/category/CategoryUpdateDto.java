package com.jewelry.api.product.dto.category;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CategoryUpdateDto {

    @NotBlank(message = "{product-category.code.not-blank}")
    @Size(min = 3, max = 10, message = "{product-category.code.size}")
    private String name;
}