package com.jewelry.api.order.service;

import com.jewelry.api.commondata.GenericServiceImpl;
import com.jewelry.api.order.converter.OrderConverter;
import com.jewelry.api.order.dto.OrderCreateDto;
import com.jewelry.api.order.dto.OrderDetailsDto;
import com.jewelry.api.order.entity.Order;
import com.jewelry.api.order.entity.OrderItem;
import com.jewelry.api.order.repository.OrderRepository;
import com.jewelry.api.order.service.itf.OrderService;
import com.jewelry.api.order.util.OrderStatus;
import com.jewelry.api.product.entity.Product;
import com.jewelry.api.security.dto.UserDetailsDto;
import com.jewelry.api.shopping.entity.Cart;
import com.jewelry.api.shopping.entity.CartItem;
import com.jewelry.api.shopping.repository.CartRepository;
import com.jewelry.api.util.MapDtoToModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Service
public class OrderServiceImpl extends GenericServiceImpl<Order, Long> implements OrderService {

    private OrderRepository orderRepository;
    private CartRepository cartRepository;

    private MapDtoToModel<Object, Order> mapper;

    @Override
    @Transactional
    public OrderDetailsDto save(OrderCreateDto dto, UserDetailsDto curUserDto) {
        Cart cart = cartRepository.findCartByUserEmail(curUserDto.getUsername());
        Order newOrder = new Order();

        newOrder = mapper.map(dto, newOrder);
        newOrder.setUser(cart.getUser());
        newOrder.setTotal(cart.getTotal());
        newOrder.setStatus(OrderStatus.NEW);

        Set<CartItem> items = new HashSet<>(cart.getItems());
        for(CartItem item : items) {
            Product product = item.getProduct();
            newOrder.addItem(new OrderItem(product, product.getPrice(), item.getQuantity()));
            product.setQuantity(product.getQuantity() - item.getQuantity());
            cart.removeItem(item);
        }
        cart.setTotal(0.0);
        cartRepository.save(cart);
        return OrderConverter.toOrderDetailsDto(orderRepository.save(newOrder));
    }
}
