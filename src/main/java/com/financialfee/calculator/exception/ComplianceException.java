package com.financialfee.calculator.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ComplianceException extends RuntimeException {
    public ComplianceException(String message) {
        super("[COMPLIANCE FAILURE] " + message);
    }
}
