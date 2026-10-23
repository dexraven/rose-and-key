package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.CompanionUpdateRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CompanionValidator {

    public void validateUpdate(CompanionUpdateRequest request) {
        if (request.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Companion date of birth cannot be in the future.");
        }
        if (request.getDateOfBirth().isBefore(LocalDate.now().minusYears(120))) {
            throw new IllegalArgumentException("Companion date of birth exceeds maximum reasonable threshold.");
        }
    }
}