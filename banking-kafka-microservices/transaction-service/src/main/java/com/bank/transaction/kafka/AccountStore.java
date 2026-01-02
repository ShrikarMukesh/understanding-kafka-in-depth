package com.bank.transaction.kafka;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AccountStore {

    private final Map<String, Double> balances = new ConcurrentHashMap<>();

    public AccountStore() {
        balances.put("A1", 1000.0);
    }

    public void debit(String accountId, double amount) {
        balances.compute(accountId, (k, v) -> {
            if (v < amount) {
                throw new RuntimeException("Insufficient funds");
            }
            return v - amount;
        });
    }
}

