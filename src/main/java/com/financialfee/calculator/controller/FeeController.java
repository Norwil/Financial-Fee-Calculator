package com.financialfee.calculator.controller;


import com.financialfee.calculator.model.FeeRequest;
import com.financialfee.calculator.model.FeeResponse;
import com.financialfee.calculator.service.FeeCalculatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fees")
@RequiredArgsConstructor
public class FeeController {

    private final FeeCalculatorService feeService;

    @PostMapping("/calculate")
    public ResponseEntity<FeeResponse> calculateFee(@RequestBody FeeRequest request) {
        return ResponseEntity.ok(feeService.calculateFee(request));
    }
}
