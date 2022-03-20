package com.jewelry.api.product.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.product.entity.Category;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends GenericRepository<Category, Long> {

    Optional<Category> findByCode(String code);

    int countByCode(String productTypeCode);

    Category findNameById(Long id);

}
