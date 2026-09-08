package com.pramod.service;

import com.pramod.repository.PaymentRepository;
import com.pramod.saga.commons.event.OrderEvent;
import com.pramod.saga.commons.event.PaymentEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public PaymentService(PaymentRepository paymentRepository,
                          KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void processPayment(OrderEvent event) {

        // Save payment to DB

        // Publish PAYMENT_COMPLETED event
    }
}
