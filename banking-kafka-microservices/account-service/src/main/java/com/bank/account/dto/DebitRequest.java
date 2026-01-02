package com.bank.account.dto;

import lombok.Data;

@Data
public class DebitRequest {
    private String transactionId;
    private String accountId;
    private double amount;
}
