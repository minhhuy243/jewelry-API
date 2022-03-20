package com.jewelry.api.order.repository;

import com.jewelry.api.commondata.GenericRepository;
import com.jewelry.api.order.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends GenericRepository<Order, Long> {
}
