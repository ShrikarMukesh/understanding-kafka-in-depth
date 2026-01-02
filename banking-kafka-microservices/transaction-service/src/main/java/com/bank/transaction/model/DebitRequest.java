package com.bank.transaction.model;

import lombok.Data;

@Data
public class DebitRequest {
    private String transactionId;
    private String accountId;
    private double amount;
}
