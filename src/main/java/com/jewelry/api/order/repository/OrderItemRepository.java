package com.jewelry.api.order.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.order.entity.OrderItem;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends GenericRepository<OrderItem, Long> {
}
