package com.jewelry.api.shopping.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.shopping.entity.CartItem;

import java.util.Optional;

public interface CartItemService extends GenericService<CartItem, Long> {

    Optional<CartItem> findItemInCart(Long cartId, String productSku);
}
