package com.jewelry.api.product.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends GenericRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    int countBySku(String sku);

    Optional<Product> findBySku(String sku);

    <T> Page<T> findProductsSummaryBy(Pageable pageable, Class<T> type);

    <T> Page<T> findShortProductsBy(Pageable pageable, Class<T> type);

    <T> Page<T> findByCategorySlug(String categorySlug, Pageable pageable, Class<T> type);

    <T> Optional<T> findBySlug(String slug, Class<T> type);

    <T> Optional<T> findById(Long id, Class<T> type);

}
