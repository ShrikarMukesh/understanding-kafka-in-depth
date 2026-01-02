package com.bank.transaction.kafka;

import com.bank.transaction.model.DebitRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
public class DebitConsumer {

    private final AccountStore store;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public DebitConsumer(AccountStore store,
                         KafkaTemplate<String, String> kafkaTemplate) {
        this.store = store;
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "debit-commands")
    public void consume(
            ConsumerRecord<String, String> record,
            Acknowledgment ack) throws Exception {

        DebitRequest request =
                mapper.readValue(record.value(), DebitRequest.class);

        store.debit(request.getAccountId(), request.getAmount());

        kafkaTemplate.send(
                "transaction-events",
                request.getTransactionId(),
                record.value()
        );

        ack.acknowledge();
    }
}
