package com.jewelry.api.order.service.itf;

import com.jewelry.api.commondata.GenericService;
import com.jewelry.api.order.dto.OrderCreateDto;
import com.jewelry.api.order.dto.OrderDetailsDto;
import com.jewelry.api.order.entity.Order;
import com.jewelry.api.security.dto.UserDetailsDto;

public interface OrderService extends GenericService<Order, Long> {

    OrderDetailsDto save(OrderCreateDto dto, UserDetailsDto curUserDto);
}
