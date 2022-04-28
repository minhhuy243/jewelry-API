package com.jewelry.api.shopping.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.shopping.entity.CartItem;
import com.jewelry.api.shopping.repository.CartItemRepository;
import com.jewelry.api.shopping.service.itf.CartItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartItemServiceImpl extends GenericServiceImpl<CartItem, Long> implements CartItemService {

    private CartItemRepository cartItemRepository;

    @Override
    public Optional<CartItem> findItemInCart(Long cartId, String productSku) {
        return cartItemRepository.findByCartIdAndProductSku(cartId, productSku);
    }
}
