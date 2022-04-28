package com.jewelry.api.shopping.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.shopping.dto.CartDetailsDto;
import com.jewelry.api.shopping.dto.CartItemCreateDto;
import com.jewelry.api.shopping.dto.CartItemUpdateDto;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.shopping.entity.CartItem;

import java.util.Optional;

public interface CartService extends GenericService<Cart, Long> {

    Cart findCartByEmail(String email);

    CartDetailsDto findCartDetails(String email);

    CartDetailsDto checkAndUpdateItemInStock(String email);

    Optional<CartItem> findItemInCart(Cart cart, String productSku);

    CartDetailsDto addItemToCart(CartItemCreateDto itemDto, String email);

    CartDetailsDto updateItemToCart(CartItemUpdateDto itemDto, String email);

    CartDetailsDto deleteItemToCart(Cart cart, CartItem item);
}
