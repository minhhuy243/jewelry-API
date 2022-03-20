package com.jewelry.api.product.controller.admin;

import com.jewelry.api.product.dto.category.CategoryCreateDto;
import com.jewelry.api.product.dto.category.CategoryUpdateDto;
import com.jewelry.api.product.entity.Category;
import com.jewelry.api.product.projection.category.CategoryWithCodeProjection;
import com.jewelry.api.product.service.itf.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/categories")
public class AdminCategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Object> findAll() {

        List<CategoryWithCodeProjection> categoryWithCodeProjections = categoryService.findAllBy(CategoryWithCodeProjection.class);
        if (categoryWithCodeProjections.isEmpty()) {
            return ResponseEntity.ok("Empty list!");
        }
        return ResponseEntity.ok(categoryWithCodeProjections);
    }

    @PostMapping("")
    public ResponseEntity<Object> addCategory(@Valid @RequestBody CategoryCreateDto dto,
                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        Category newCategory = categoryService.save(dto);
        return ResponseEntity.ok(newCategory);
    }

    @PutMapping("/{category-id}")
    public ResponseEntity<Object> updateCategory(@PathVariable("category-id") Long id,
                                                 @Valid @RequestBody CategoryUpdateDto dto,
                                                 BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        Category categoryUpdate = categoryService.updateCategoryInfo(dto, id);
        return ResponseEntity.ok(categoryUpdate);
    }

    @DeleteMapping("/{category-id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable("category-id") Long id) {

        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            return ResponseEntity.badRequest().body("Category not found!");
        }
        categoryService.deleteById(id);
        return ResponseEntity.ok("Successful delete!");
    }
}
