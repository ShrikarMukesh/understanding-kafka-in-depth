package com.bank.account.controller;


import com.bank.account.dto.DebitRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public AccountController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/debit")
    public ResponseEntity<Void> debit(@RequestBody DebitRequest request) {
        try {
            kafkaTemplate.send(
                    "debit-commands",
                    request.getAccountId(),       // KEY = accountId (ordering)
                    mapper.writeValueAsString(request)
            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing request", e);
        }

        return ResponseEntity.accepted().build();
    }
}
