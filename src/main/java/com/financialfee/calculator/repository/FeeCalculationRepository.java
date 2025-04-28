package com.financialfee.calculator.repository;

import com.financialfee.calculator.model.FeeCalculation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeeCalculationRepository extends JpaRepository<FeeCalculation, Long> {
}
