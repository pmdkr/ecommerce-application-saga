package com.pramod.service;

import com.pramod.saga.commons.event.OrderEvent;
import com.pramod.saga.commons.event.OrderStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceConsumer {

    private final PaymentService paymentService;
    private final Logger log = LoggerFactory.getLogger(PaymentServiceConsumer.class);

    public PaymentServiceConsumer(PaymentService paymentService) {

        log.info("Payment Service Consumer Bean is created");
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-events", groupId = "payment-group")
    public void consume(OrderEvent event) {

        System.out.println("Recived Order: "
                + event.getOrderRequestDto().getOrderId());


        if (event.getOrderStatus() == OrderStatus.ORDER_CREATED) {
            //Process payment

        }

    }
}
