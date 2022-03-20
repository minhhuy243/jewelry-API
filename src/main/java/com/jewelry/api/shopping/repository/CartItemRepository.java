package com.jewelry.api.shopping.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.shopping.entity.CartItem;
import com.jewelry.api.shopping.projection.CartItemWithQuantityProjection;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends GenericRepository<CartItem, Long> {

    Optional<CartItem> findByCartIdAndProductSku(Long cartId, String productSku);

//    Optional<List<CartItemWithQuantityProjection>> findCartItemsWithQuantityByCartId(Long cartId);
}
