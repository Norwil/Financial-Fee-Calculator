package com.financialfee.calculator.model;


public enum PartnerCode {
    BANK_US(0.9),    // 10% discount
    FINTECH_X(0.85), // 15% discount
    DEFAULT(1.0);     // No discount

    private final double discountRate;

    PartnerCode(double discountRate) {
        this.discountRate = discountRate;
    }

    public double getDiscountRate() {
        return discountRate;
    }
}