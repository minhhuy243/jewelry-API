package com.jewelry.api.product.controller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jewelry.api.commondata.model.PagedResult;
import com.jewelry.api.commondata.model.SearchCriteria;
import com.jewelry.api.product.dto.product.*;
import com.jewelry.api.product.entity.Product;
import com.jewelry.api.product.projection.product.ProductDetailsAdminProjection;
import com.jewelry.api.product.projection.product.ProductSummaryProjection;
import com.jewelry.api.product.service.itf.ProductService;
import com.jewelry.api.util.ErrorUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private ProductService productService;

    @GetMapping("")
    public ResponseEntity<Object> findAll(
            @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(value = "searchCriteria", required = false) String searchCriteriaStr) throws JsonProcessingException {

        PagedResult<ProductSummaryDto> productSummaryPaged;
        if(searchCriteriaStr != null) {
            SearchCriteria searchCriteria = new ObjectMapper().readValue(searchCriteriaStr, SearchCriteria.class);
            productSummaryPaged = productService.findProductsSummaryWithSearch(searchCriteria, pageable);
        } else {
            productSummaryPaged = productService.findProductsSummary(pageable);
        }
        return ResponseEntity.ok(productSummaryPaged);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Object> findProductById(@PathVariable(value = "productId") Long productId) {

        ProductDetailsAdminDto product = productService.findProductDetailsAdminById(productId);
        if(product == null) {
            return ResponseEntity.badRequest().body("Product not found!");
        }
        return ResponseEntity.ok(product);
    }


    @PostMapping(value = "",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> addProduct(@RequestPart("dto") @Valid ProductCreateDto dto,
                                             @RequestPart(value = "images[]", required = false) List<MultipartFile> images,
                                             @RequestPart(value = "avatar", required = false) MultipartFile avatar,
                                             BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.getErrorMessages(bindingResult));
        }
        if(images != null) dto.setImages(images);
        if(avatar != null) dto.setAvatar(avatar);

        Product newProduct = productService.save(dto);
        return ResponseEntity.ok(newProduct);
    }

    @PutMapping(value = "/{productId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> updateProduct(@PathVariable("productId") Long id,
                                                @RequestPart("dto") @Valid ProductUpdateDto dto,
                                                @RequestPart(value = "images[]", required = false) List<MultipartFile> images,
                                                @RequestPart(value = "avatar", required = false) MultipartFile avatar,
                                                BindingResult bindingResult) throws GeneralSecurityException, IOException {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorUtils.getErrorMessages(bindingResult));
        }

        if(images != null) dto.setNewImages(images);
        if(avatar != null) dto.setAvatar(avatar);

        Product productUpdated = productService.updateProductInfo(dto, id);
        return ResponseEntity.ok("Successful update!");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Object> deleteProduct(@PathVariable("productId") Long id) {

        Optional<Product> product = productService.findById(id);
        if (!product.isPresent()) {
            return ResponseEntity.badRequest().body("Product not found!");
        }
        productService.deleteById(id);
        return ResponseEntity.ok("Successful delete!");
    }

}
