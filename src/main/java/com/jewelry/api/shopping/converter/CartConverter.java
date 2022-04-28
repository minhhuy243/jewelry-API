package com.jewelry.api.shopping.converter;

import com.jewelry.api.product.dto.product.ShortProductDto;
import com.jewelry.api.product.entity.Product;
import com.jewelry.api.shopping.dto.CartDetailsDto;
import com.jewelry.api.shopping.dto.CartItemDetailsDto;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.shopping.entity.CartItem;
import com.jewelry.api.shopping.projection.CartItemWithQuantityProjection;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CartConverter {

    public static CartDetailsDto toCartDetailsDto(Cart cart) {
        List<CartItemDetailsDto> items = cart.getItems().stream()
                .sorted(Comparator.comparing(CartItem:: getCreatedAt))
                .map(item -> {
                    Product product = item.getProduct();
                    return CartItemDetailsDto.builder()
                            .product(new ShortProductDto(
                                    product.getSku(), product.getName(), product.getPrice(), product.getAvatar(),
                                    product.getSlug(), null, product.getCategory().getSlug()))
                            .quantity(item.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());

        return CartDetailsDto.builder()
                .total(cart.getTotal())
                .items(items)
                .build();
    }

    public static CartDetailsDto toCartDetailsDto(Double total, List<CartItemWithQuantityProjection> itemProjections) {
        List<CartItemDetailsDto> items = itemProjections.stream()
                .sorted(Comparator.comparing(CartItemWithQuantityProjection :: getCreatedAt))
                .map(item -> CartItemDetailsDto.builder()
                        .product(new ShortProductDto(
                                item.getProductSku(), item.getProductName(), item.getProductPrice(), item.getProductAvatar(),
                                item.getProductSlug(), null, item.getProductCategorySlug()))
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());

        return CartDetailsDto.builder()
                .total(total)
                .items(items)
                .build();
    }
}
