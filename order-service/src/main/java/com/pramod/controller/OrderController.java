package com.pramod.controller;

import com.pramod.entity.PurchaseOrder;
import com.pramod.saga.commons.dto.OrderRequestDto;
import com.pramod.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/order")
public class OrderController {

    public final OrderService orderService;
    //constructor injection
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<PurchaseOrder> createOrder(@RequestBody OrderRequestDto orderRequestDto) {

        PurchaseOrder res = orderService.createOrder(orderRequestDto);
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
}
