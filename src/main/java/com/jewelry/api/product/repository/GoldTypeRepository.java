package com.jewelry.api.product.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.product.entity.GoldType;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GoldTypeRepository extends GenericRepository<GoldType, Long> {

    Optional<GoldType> findByPercentage(Double percentage);

    int countByPercentage(Double percentage);
}
