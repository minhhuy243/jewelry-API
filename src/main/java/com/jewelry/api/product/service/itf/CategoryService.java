package com.jewelry.api.product.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.product.dto.category.CategoryCreateDto;
import com.jewelry.api.product.dto.category.CategoryUpdateDto;
import com.jewelry.api.product.entity.Category;

public interface CategoryService extends GenericService<Category, Long> {

    Category save(CategoryCreateDto dto);

    Category updateCategoryInfo(CategoryUpdateDto dto, Long id);

}
