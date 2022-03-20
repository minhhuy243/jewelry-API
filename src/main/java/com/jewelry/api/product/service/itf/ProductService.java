package com.jewelry.api.product.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.commondata.model.PagedResult;
import com.jewelry.api.commondata.model.SearchCriteria;
import com.jewelry.api.product.dto.product.*;
import com.jewelry.api.product.entity.Product;
import com.jewelry.api.product.projection.product.ProductDetailsAdminProjection;
import com.jewelry.api.product.projection.product.ProductSummaryProjection;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface ProductService extends GenericService<Product, Long> {

    Product save(ProductCreateDto dto);

    Product updateProductInfo(ProductUpdateDto dto, Long id) throws GeneralSecurityException, IOException;

    PagedResult<ShortProductDto> findShortProductsWithSearch(SearchCriteria searchCriteria, Pageable pageable);

    PagedResult<ShortProductDto> findShortProducts(Pageable pageable);

    PagedResult<ShortProductDto> findShortProductsByCategory(String categorySlug, Pageable pageable);

    ProductDetailsDto findProductDetailsBySlug(String slug);

    /* ADMIN */
    PagedResult<ProductSummaryDto> findProductsSummary(Pageable pageable);

    PagedResult<ProductSummaryDto> findProductsSummaryWithSearch(SearchCriteria searchCriteria, Pageable pageable);

    ProductDetailsAdminDto findProductDetailsAdminById(Long id);
}
