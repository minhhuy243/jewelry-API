package com.jewelry.api.order.converter;

import com.jewelry.api.order.dto.OrderDetailsDto;
import com.jewelry.api.order.dto.OrderItemDetailsDto;
import com.jewelry.api.order.entity.Order;
import com.jewelry.api.order.entity.OrderItem;
import com.jewelry.api.product.dto.product.ShortProductDto;
import com.jewelry.api.product.entity.Product;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OrderConverter {

    public static OrderDetailsDto toOrderDetailsDto(Order order) {
        List<OrderItemDetailsDto> items = order.getItems().stream()
                .sorted(Comparator.comparing(OrderItem::getCreatedAt))
                .map(item -> {
                    Product product = item.getProduct();
                    return OrderItemDetailsDto.builder()
                            .product(new ShortProductDto(
                                    product.getSku(), product.getName(), product.getPrice(), product.getAvatar(),
                                    product.getSlug(), null, product.getCategory().getSlug()))
                            .price(product.getPrice())
                            .quantity(item.getQuantity())
                            .build();
                })
                .collect(Collectors.toList());

        return OrderDetailsDto.builder()
                .id(order.getId())
                .total(order.getTotal())
                .status(order.getStatus())
                .fullName(order.getFullName())
                .address(order.getAddress())
                .phoneNumber(order.getPhoneNumber())
                .content(order.getContent())
                .items(items)
                .build();
    }
}