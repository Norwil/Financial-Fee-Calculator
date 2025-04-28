package com.financialfee.calculator.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeeRequest {
    private TransactionType transactionType;
    private double amount;
    private boolean isBusinessAccount;
    private PartnerCode partnerCode;
}
