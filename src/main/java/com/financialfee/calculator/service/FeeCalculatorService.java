package com.financialfee.calculator.service;

import com.financialfee.calculator.exception.ComplianceException;
import com.financialfee.calculator.model.*;
import com.financialfee.calculator.repository.AuditLogRepository;
import com.financialfee.calculator.repository.FeeCalculationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FeeCalculatorService {

    private final FeeCalculationRepository repository;
    private final AuditLogRepository auditLogRepo;

    // Base fees configuration
    private static final Map<TransactionType, Double> BASE_FEES = Map.of(
            TransactionType.WIRE_DOMESTIC, 25.0,
            TransactionType.WIRE_INTERNATIONAL, 45.0,
            TransactionType.ACH_OUTGOING, 0.25
    );

    // Transaction limits
    private static final Map<TransactionType, Double> MAX_LIMITS = Map.of(
            TransactionType.WIRE_DOMESTIC, 10_000.0,
            TransactionType.WIRE_INTERNATIONAL, 50_000.0
    );

    public FeeResponse calculateFee(FeeRequest request) {
        try {
            validateTransactionlimit(request);
            double finalFee = calculateFinalFee(request);
            saveCalculation(request, finalFee);
            logAudit(request, "COMPLIANCE PASSED");
            return createResponse(request, finalFee);

        } catch (ComplianceException e) {
            logAudit(request, "COMPLIANCE FAILED: " + e.getMessage());
            throw e;
        }
    }

    // Helper methods
    private double calculateFinalFee(FeeRequest request) {
        double baseFee = BASE_FEES.getOrDefault(request.getTransactionType(), 0.0);
        return applyDiscounts(baseFee, request);
    }

    private double applyDiscounts(double baseFee, FeeRequest request) {
        double businessDiscount = request.isBusinessAccount() ? 0.8 : 1.0;
        double partnerDiscount = request.getPartnerCode() != null ?
                                request.getPartnerCode().getDiscountRate() :
                                PartnerCode.DEFAULT.getDiscountRate();

        return baseFee * businessDiscount * partnerDiscount;
    }

    private void validateTransactionlimit(FeeRequest request) {
        Double maxLimit = MAX_LIMITS.get(request.getTransactionType());
        if (maxLimit != null && request.getAmount() > maxLimit) {
            throw new ComplianceException(
                    "Amount exceeds " + maxLimit + " limit for " + request.getTransactionType()
            );
        }
    }

    private void saveCalculation(FeeRequest request, double finalFee) {
        FeeCalculation calculation = FeeCalculation.builder()
                .transactionType(request.getTransactionType())
                .amount(request.getAmount())
                .calculatedFee(finalFee)
                .businessAccount(request.isBusinessAccount())
                .partnerCode(request.getPartnerCode())
                .timestamp(LocalDateTime.now())
                .build();

        repository.save(calculation);
    }

    private FeeResponse createResponse(FeeRequest request, double finalFee) {
        return new FeeResponse(
                request.getTransactionType(),
                request.getAmount(),
                finalFee,
                LocalDateTime.now()
        );
    }

    private void logAudit(FeeRequest request, String status) {
        AuditLog log = AuditLog.builder()
                .timestamp(LocalDateTime.now())
                .amount(request.getAmount())
                .complianceCheckResult(status)
                .build();

        auditLogRepo.save(log);
    }
}
