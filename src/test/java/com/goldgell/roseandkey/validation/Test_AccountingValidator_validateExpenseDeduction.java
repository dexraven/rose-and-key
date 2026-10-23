package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_AccountingValidator_validateExpenseDeduction extends Test_AccountingValidator {

    @Test
    @DisplayName("Happy Path: Validates standard past expense deduction")
    void shouldPassValidExpense() {
        ExpenseDeductionRequest request = ExpenseDeductionRequest.builder()
                .description("Annual D23 Gold Membership")
                .category(ExpenseCategory.PROFESSIONAL_DUES_AND_FEES)
                .amountCents(12999L)
                .expenseDate(LocalDate.now().minusDays(10))
                .build();

        assertThatCode(() -> validator.validateExpenseDeduction(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Negative Scenario: Rejects expense with future date")
    void shouldRejectFutureExpense() {
        ExpenseDeductionRequest request = ExpenseDeductionRequest.builder()
                .description("Future FAM Trip Flight")
                .amountCents(50000L)
                .expenseDate(LocalDate.now().plusDays(2))
                .build();

        assertThatThrownBy(() -> validator.validateExpenseDeduction(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Expense date cannot be in the future");
    }

    @Test
    @DisplayName("Negative Scenario: Rejects expense with zero or negative amount")
    void shouldRejectZeroOrNegativeAmount() {
        ExpenseDeductionRequest request = ExpenseDeductionRequest.builder()
                .amountCents(0L)
                .expenseDate(LocalDate.now().minusDays(1))
                .build();

        assertThatThrownBy(() -> validator.validateExpenseDeduction(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Deductible amount must be strictly greater than zero cents");
    }
}