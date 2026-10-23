package com.goldgell.roseandkey.domain.accounting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_ExpenseDeduction {

    @Test
    @DisplayName("POJO / Entity: Tests getters, setters, all-args, no-args, and mutation")
    void testPojoAndMutations() {
        ExpenseDeduction deduction = new ExpenseDeduction();
        UUID id = UUID.randomUUID();
        LocalDate expenseDate = LocalDate.of(2026, 8, 10);
        Instant now = Instant.now();

        deduction.setId(id);
        deduction.setAgentId("agent-001");
        deduction.setExpenseDescription("Disney Travel Agent Training Portal Subscription");
        deduction.setCategory(ExpenseDeduction.ExpenseCategory.SOFTWARE_SUBSCRIPTIONS);
        deduction.setAmountCents(25000L);
        deduction.setExpenseDate(expenseDate);
        deduction.setReceiptDocumentUrl("https://storage.roseandkey.com/receipts/rec-1.pdf");
        deduction.setCreatedAt(now);

        assertThat(deduction.getId()).isEqualTo(id);
        assertThat(deduction.getAgentId()).isEqualTo("agent-001");
        assertThat(deduction.getExpenseDescription()).isEqualTo("Disney Travel Agent Training Portal Subscription");
        assertThat(deduction.getCategory()).isEqualTo(ExpenseDeduction.ExpenseCategory.SOFTWARE_SUBSCRIPTIONS);
        assertThat(deduction.getAmountCents()).isEqualTo(25000L);
        assertThat(deduction.getExpenseDate()).isEqualTo(expenseDate);
        assertThat(deduction.getReceiptDocumentUrl()).isEqualTo("https://storage.roseandkey.com/receipts/rec-1.pdf");
        assertThat(deduction.getCreatedAt()).isEqualTo(now);

        ExpenseDeduction built = ExpenseDeduction.builder()
                .category(ExpenseDeduction.ExpenseCategory.FAM_TRAVEL_AND_LODGING)
                .build();
        assertThat(built.getCategory()).isEqualTo(ExpenseDeduction.ExpenseCategory.FAM_TRAVEL_AND_LODGING);
        assertThat(built.getCreatedAt()).isNotNull();
    }
}