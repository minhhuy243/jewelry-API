package com.jewelry.api.product.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.commondata.Slug;
import com.jewelry.api.product.dto.category.CategoryCreateDto;
import com.jewelry.api.product.dto.category.CategoryUpdateDto;
import com.jewelry.api.product.entity.Category;
import com.jewelry.api.product.repository.CategoryRepository;
import com.jewelry.api.product.service.itf.CategoryService;
import com.jewelry.api.util.MapDtoToModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CategoryServiceImpl extends GenericServiceImpl<Category, Long> implements CategoryService {

    private CategoryRepository categoryRepository;
    private MapDtoToModel<Object, Category> mapper;

    @Override
    public Category save(CategoryCreateDto dto) {
        Category newCategory = new Category();
        newCategory = mapper.map(dto, newCategory);
        newCategory.setSlug(new Slug().slugify(newCategory.getName()));
        return categoryRepository.save(newCategory);
    }

    @Override
    public Category updateCategoryInfo(CategoryUpdateDto dto, Long id) {
        Category categoryUpdated = categoryRepository.getOne(id);
        categoryUpdated = mapper.map(dto, categoryUpdated);
        return categoryRepository.save(categoryUpdated);
    }

}
