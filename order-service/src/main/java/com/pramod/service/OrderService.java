package com.pramod.service;


import com.pramod.entity.PurchaseOrder;
import com.pramod.repository.OrderRepository;
import com.pramod.saga.commons.dto.OrderRequestDto;
import com.pramod.saga.commons.dto.OrderResponseDto;
import com.pramod.saga.commons.event.OrderEvent;
import com.pramod.saga.commons.event.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderService {

    public final OrderRepository orderRepository;
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderService(OrderRepository orderRepository
            , KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.orderRepository = orderRepository;
        this.kafkaTemplate = kafkaTemplate;
    }


    public PurchaseOrder createOrder(OrderRequestDto orderRequestDto) {


        //save the order to DB
        PurchaseOrder order = orderRepository.save(convertDtoToPurchaseOrder(orderRequestDto));
        orderRequestDto.setOrderId(order.getOrderId());
        log.info("Order is Created with order id : " + order.getOrderId());


        // Create kafka event with ORDER_STATUS CREATED
        OrderEvent event = new OrderEvent(orderRequestDto, OrderStatus.ORDER_CREATED);

        //publish event
        kafkaTemplate.send("order-events",
                String.valueOf(order.getOrderId()),
                event);

        log.info("Published ORDER_CREATED event for the order {}", order.getOrderId());

        return order;


    }


    public List<OrderResponseDto> getAllOrder() {
        List<PurchaseOrder> response = orderRepository.findAll();

        List<OrderResponseDto> result = response.stream().map(dto -> PurchaseOrderToDto(dto)).collect(Collectors.toList());

        return result;


    }


    public PurchaseOrder convertDtoToPurchaseOrder(OrderRequestDto dto) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();


        purchaseOrder.setUserId(dto.getUserId());
        purchaseOrder.setProductId(dto.getProductId());
        purchaseOrder.setOrderStatus(OrderStatus.ORDER_CREATED);
        purchaseOrder.setPrice(dto.getPrice());
        return purchaseOrder;
    }


    public OrderResponseDto PurchaseOrderToDto(PurchaseOrder order) {
        OrderResponseDto dto = new OrderResponseDto();

        dto.setUserId(order.getUserId());
        dto.setOrderId(order.getOrderId());
        dto.setUserId(order.getUserId());
        dto.setProductId(order.getProductId());
        dto.setPrice(order.getPrice());
        dto.setOrderStatus(order.getOrderStatus());
        return dto;

    }

}


