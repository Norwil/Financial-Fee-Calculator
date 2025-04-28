package com.financialfee.calculator;

import com.financialfee.calculator.exception.ComplianceException;
import com.financialfee.calculator.model.*;
import com.financialfee.calculator.repository.AuditLogRepository;
import com.financialfee.calculator.repository.FeeCalculationRepository;
import com.financialfee.calculator.service.FeeCalculatorService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;

public class ManualTest {
    public static void main(String[] args) {
        // Initialize service with test repositories
        FeeCalculatorService service = new FeeCalculatorService(
                new TestFeeRepo(),
                new TestAuditRepo()
        );

        // Test 1: Valid calculation
        testValidFeeCalculation(service);

        // Test 2: Transaction limit exceeded
        testLimitExceeded(service);
    }

    private static void testValidFeeCalculation(FeeCalculatorService service) {
        System.out.println("=== Testing Valid Calculation ===");
        FeeRequest request = new FeeRequest();
        request.setTransactionType(TransactionType.WIRE_DOMESTIC);
        request.setAmount(5000.0);
        request.setBusinessAccount(true);
        request.setPartnerCode(PartnerCode.BANK_US);

        try {
            FeeResponse response = service.calculateFee(request);
            System.out.println("Fee: " + response.getCalculatedFee()); // Should be 18.0
        } catch (Exception e) {
            System.out.println("Test Failed: " + e.getMessage());
        }
    }

    private static void testLimitExceeded(FeeCalculatorService service) {
        System.out.println("\n=== Testing Limit Exceeded ===");
        FeeRequest request = new FeeRequest();
        request.setTransactionType(TransactionType.WIRE_DOMESTIC);
        request.setAmount(15000.0); // Over 10k limit

        try {
            service.calculateFee(request);
            System.out.println("Test Failed: No exception thrown");
        } catch (ComplianceException e) {
            System.out.println("Test Passed: " + e.getMessage());
        }
    }
}

// Minimal Test Repositories (Only implement used methods)
class TestFeeRepo implements FeeCalculationRepository {
    private final List<FeeCalculation> db = new ArrayList<>();

    @Override
    public FeeCalculation save(FeeCalculation entity) {
        db.add(entity);
        return entity;
    }

    @Override
    public <S extends FeeCalculation> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<FeeCalculation> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    // Dummy implementations for other methods
    @Override public List<FeeCalculation> findAll() { return Collections.emptyList(); }

    @Override
    public List<FeeCalculation> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(FeeCalculation entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends FeeCalculation> entities) {

    }

    @Override
    public void deleteAll() {

    }


    @Override
    public void flush() {

    }

    @Override
    public <S extends FeeCalculation> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends FeeCalculation> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<FeeCalculation> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public FeeCalculation getOne(Long aLong) {
        return null;
    }

    @Override
    public FeeCalculation getById(Long aLong) {
        return null;
    }

    @Override
    public FeeCalculation getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends FeeCalculation> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends FeeCalculation> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends FeeCalculation> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends FeeCalculation> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends FeeCalculation> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends FeeCalculation> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends FeeCalculation, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<FeeCalculation> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<FeeCalculation> findAll(Pageable pageable) {
        return null;
    }
    // Add other methods with empty implementations as needed
}

class TestAuditRepo implements AuditLogRepository {
    @Override
    public AuditLog save(AuditLog entity) {
        return entity; // No-op for testing
    }

    @Override
    public <S extends AuditLog> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<AuditLog> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    // Dummy implementations
    @Override public List<AuditLog> findAll() { return Collections.emptyList(); }

    @Override
    public List<AuditLog> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override public void delete(AuditLog entity) {}

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends AuditLog> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends AuditLog> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends AuditLog> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<AuditLog> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public AuditLog getOne(Long aLong) {
        return null;
    }

    @Override
    public AuditLog getById(Long aLong) {
        return null;
    }

    @Override
    public AuditLog getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends AuditLog> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends AuditLog> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends AuditLog> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends AuditLog> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends AuditLog> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends AuditLog> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends AuditLog, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public List<AuditLog> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<AuditLog> findAll(Pageable pageable) {
        return null;
    }
}