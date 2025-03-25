package com.bank.model;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class StatementResponse {
    private String name;
    private String email;
    private double openingBalance;
    private double closingBalance;
    private String accountNumber;
    private String statementPeriod;
} 