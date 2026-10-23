package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class AccountingValidator {

    public void validateExpenseDeduction(ExpenseDeductionRequest request) {
        if (request.getExpenseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Expense date cannot be in the future.");
        }
        if (request.getAmountCents() <= 0) {
            throw new IllegalArgumentException("Deductible amount must be strictly greater than zero cents.");
        }
    }

    public void validateQuarter(int quarter) {
        if (quarter < 1 || quarter > 4) {
            throw new IllegalArgumentException("Tax quarter must be between 1 and 4.");
        }
    }
}