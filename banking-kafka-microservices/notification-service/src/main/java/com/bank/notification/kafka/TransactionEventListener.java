package com.bank.notification.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener {

    @KafkaListener(topics = "transaction-events")
    public void listen(String event) {
        System.out.println("NOTIFICATION SENT: " + event);
    }
}
