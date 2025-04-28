package com.financialfee.calculator.repository;

import com.financialfee.calculator.model.FeeCalculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeeCalculationRepository extends JpaRepository<FeeCalculation, Long> {
}
