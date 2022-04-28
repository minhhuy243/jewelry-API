package com.jewelry.api.product.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.product.entity.Image;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ImageRepository extends GenericRepository<Image, Long> {

    //    @Query(value = "SELECT i.gDriveId " +
//                    "FROM " +
//                    "   Image i " +
//                    "LEFT JOIN " +
//                    "   Product p " +
//                    "ON i.product.id = p.id " +
//                    "WHERE p.sku = ?1")
    @Query(value = "SELECT i.gDriveId FROM Image i WHERE i.product.sku = ?1")
    Set<String> findGDriveIdByProductSku(String productSku);

    @Query(value = "SELECT i FROM Image i WHERE i.gDriveId IN (?1)")
    Set<Image> findByGDriveIdIn(List<String> gDriveId);
}
