package com.financialfee.calculator.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class FeeResponse {
    private TransactionType transactionType;
    private double amount;
    private double calculatedFee;
    private LocalDateTime timestamp;
}
