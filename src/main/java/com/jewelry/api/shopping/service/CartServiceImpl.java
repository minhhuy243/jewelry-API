package com.jewelry.api.shopping.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.product.repository.ProductRepository;
import com.jewelry.api.shopping.converter.CartConverter;
import com.jewelry.api.shopping.dto.CartItemCreateDto;
import com.jewelry.api.shopping.dto.CartItemUpdateDto;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.shopping.entity.CartItem;
import com.jewelry.api.shopping.dto.CartDetailsDto;
import com.jewelry.api.shopping.repository.CartItemRepository;
import com.jewelry.api.shopping.repository.CartRepository;
import com.jewelry.api.shopping.service.itf.CartService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CartServiceImpl extends GenericServiceImpl<Cart, Long> implements CartService {

    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;
    private ProductRepository productRepository;

    @Override
    public Cart findCartByEmail(String email) {
        return cartRepository.findCartByUserEmail(email);
    }

    @Override
    public Optional<CartItem> findItemInCart(Cart cart, String productSku) {
        return cartItemRepository.findByCartIdAndProductSku(cart.getId(), productSku);
    }

    @Override
    public CartDetailsDto findCartDetails(String email) {
//        CartWithTotalProjection cartWithTotalProjection
//                = cartRepository.findCartWithTotalProjectionByUserEmail(email);
//        Optional<List<CartItemWithQuantityProjection>> cartItemWithQuantityProjections
//                = cartItemRepository.findCartItemsWithQuantityByCartId(cartWithTotalProjection.getId());
//
//        return cartItemWithQuantityProjections
//                .map(item -> CartConverter.toCartDetailsDto(cartWithTotalProjection.getTotal(), item))
//                .orElse(null);
        return null;
    }

    @Override
    public CartDetailsDto checkAndUpdateItemInStock(String email) {
        Cart cart = cartRepository.findCartByUserEmail(email);
        if(cart.getItems() != null) {
            List <CartItem> items = cart.getItems().stream()
                    .filter(item -> item.getQuantity() > item.getProduct().getQuantity())
                    .collect(Collectors.toList());
            for(CartItem item : items) {
                cart.setTotal(cart.getTotal() - (item.getQuantity() * item.getProduct().getPrice()));
                cart.removeItem(item);
            }
            return CartConverter.toCartDetailsDto(cartRepository.save(cart));
        }
        return null;
    }

    @Override
    @Transactional
    public CartDetailsDto addItemToCart(CartItemCreateDto itemDto, String email) {
        Cart cart = cartRepository.findCartByUserEmail(email);
        Optional<CartItem> cartItemOpt
                = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getSku().equals(itemDto.getProductSku())).findFirst();
        Double totalPrice = cart.getTotal();

        if(cartItemOpt.isPresent()) {
            cartItemOpt.get().setQuantity(cartItemOpt.get().getQuantity() + itemDto.getQuantity());
            totalPrice += cartItemOpt.get().getProduct().getPrice() * itemDto.getQuantity();
        } else {
            CartItem item = new CartItem();
            item.setProduct(productRepository.findBySku(itemDto.getProductSku()).get());
            item.setQuantity(itemDto.getQuantity());
            cart.addItem(item);
            totalPrice += item.getProduct().getPrice() * item.getQuantity();
        }

        cart.setTotal(totalPrice);
        return CartConverter.toCartDetailsDto(cartRepository.save(cart));
    }

    @Override
    @Transactional
    public CartDetailsDto updateItemToCart(CartItemUpdateDto itemDto, String email) {
        Cart cart = cartRepository.findCartByUserEmail(email);
        Optional<CartItem> cartItemOpt
                = cart.getItems().stream()
                .filter(cartItem -> cartItem.getProduct().getSku().equals(itemDto.getProductSku())).findFirst();

        int diffQuantity = itemDto.getQuantity() - cartItemOpt.get().getQuantity();
        cartItemOpt.get().setQuantity(itemDto.getQuantity());
        cart.setTotal(cart.getTotal() + (diffQuantity * cartItemOpt.get().getProduct().getPrice()));
        return CartConverter.toCartDetailsDto(cartRepository.save(cart));
    }

    @Override
    public CartDetailsDto deleteItemToCart(Cart cart, CartItem item) {
        cart.removeItem(item);
        cart.setTotal(cart.getTotal() - (item.getQuantity() * item.getProduct().getPrice()));
        return CartConverter.toCartDetailsDto(cartRepository.save(cart));
    }

}
