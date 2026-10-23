package com.goldgell.roseandkey.repository.accounting;

import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Test_ExpenseDeductionRepository_findByAgentIdAndExpenseDateBetween extends Test_ExpenseDeductionRepository {

    @Test
    @DisplayName("Happy Path: Retrieves expenses within date window for matching agent")
    void shouldFindExpensesInDateWindow() {
        LocalDate inRangeDate = LocalDate.of(2026, 4, 15);
        LocalDate outOfRangeDate = LocalDate.of(2026, 7, 20);

        ExpenseDeduction inRange = persistExpense("agent-kelley-01", inRangeDate, 15000L);
        persistExpense("agent-kelley-01", outOfRangeDate, 30000L);
        persistExpense("agent-other-02", inRangeDate, 20000L);

        List<ExpenseDeduction> results = expenseDeductionRepository.findByAgentIdAndExpenseDateBetween(
                "agent-kelley-01",
                LocalDate.of(2026, 4, 1),
                LocalDate.of(2026, 6, 30)
        );

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getId()).isEqualTo(inRange.getId());
        assertThat(results.getFirst().getAmountCents()).isEqualTo(15000L);
    }

    @Test
    @DisplayName("Edge Case: Returns empty list when no expenses match date bounds")
    void shouldReturnEmptyWhenNoExpensesInPeriod() {
        persistExpense("agent-kelley-01", LocalDate.of(2026, 1, 10), 5000L);

        List<ExpenseDeduction> results = expenseDeductionRepository.findByAgentIdAndExpenseDateBetween(
                "agent-kelley-01",
                LocalDate.of(2026, 8, 1),
                LocalDate.of(2026, 8, 31)
        );

        assertThat(results).isEmpty();
    }
}