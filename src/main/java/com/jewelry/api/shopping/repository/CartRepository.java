package com.jewelry.api.shopping.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.shopping.projection.CartWithTotalProjection;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends GenericRepository<Cart, Long> {

    Cart findCartByUserEmail(String email);

}
