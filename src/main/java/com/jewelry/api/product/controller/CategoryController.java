package com.jewelry.api.product.controller;

import com.jewelry.api.product.projection.category.CategoryWithSlugProjection;
import com.jewelry.api.product.service.itf.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Object> findAll() {

        List<CategoryWithSlugProjection> categoryWithSlugProjection
                = categoryService.findAllBy(CategoryWithSlugProjection.class);
        if (categoryWithSlugProjection.isEmpty()) {
            return ResponseEntity.ok("Empty list!");
        }
        return ResponseEntity.ok(categoryWithSlugProjection);
    }

}
