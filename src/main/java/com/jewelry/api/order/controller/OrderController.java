package com.jewelry.api.order.controller;

import com.jewelry.api.order.dto.OrderCreateDto;
import com.jewelry.api.order.dto.OrderDetailsDto;
import com.jewelry.api.order.service.itf.OrderService;
import com.jewelry.api.security.dto.UserDetailsDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;

    @PostMapping("")
    public ResponseEntity<Object> createNewOrder(@Valid @RequestBody OrderCreateDto dto,
                                                 @AuthenticationPrincipal UserDetailsDto curUserDto,
                                                 BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult);
        }

        OrderDetailsDto orderDetailsDto = orderService.save(dto, curUserDto);
        return ResponseEntity.ok(orderDetailsDto);
    }
}
