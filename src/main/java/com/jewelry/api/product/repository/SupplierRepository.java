package com.jewelry.api.product.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.product.entity.Supplier;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends GenericRepository<Supplier, Long> {

    Optional<Supplier> findByCode(String code);

    int countByCode(String supplierCode);
}
