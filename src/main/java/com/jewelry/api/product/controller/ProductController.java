package com.jewelry.api.product.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jewelry.api.commondata.model.FilterCriteria;
import com.jewelry.api.commondata.model.PagedResult;
import com.jewelry.api.commondata.model.SearchCriteria;
import com.jewelry.api.product.dto.product.ProductDetailsDto;
import com.jewelry.api.product.dto.product.ShortProductDto;
import com.jewelry.api.product.service.itf.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<Object> findProducts(
            @PageableDefault(size = 9, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "searchCriteria", required = false) String searchCriteriaStr,
            @RequestParam(required = false) FilterCriteria filterCriteria) throws JsonProcessingException {

        PagedResult<ShortProductDto> shortProducts;

        if(searchCriteriaStr != null) {
            SearchCriteria searchCriteria = new ObjectMapper().readValue(searchCriteriaStr, SearchCriteria.class);
            shortProducts = productService.findShortProductsWithSearch(searchCriteria, pageable);
        } else {
            shortProducts = productService.findShortProducts(pageable);
        }
        return ResponseEntity.ok(shortProducts);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<Object> findProductDetails(@PathVariable(value = "slug") String slug) {
        ProductDetailsDto productDetails = productService.findProductDetailsBySlug(slug);
        if(productDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetails);
    }

    @GetMapping("/categories/{categorySlug}")
    public ResponseEntity<Object> findProductsByCategory(@PathVariable(value = "categorySlug") String categorySlug,
                                                         @PageableDefault(size = 9, sort = "createdAt",
                                                                 direction = Sort.Direction.DESC) Pageable pageable) {
        PagedResult<ShortProductDto> shortProducts = productService.findShortProductsByCategory(categorySlug, pageable);
        return ResponseEntity.ok(shortProducts);
    }

}
