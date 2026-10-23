package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Test_AccountingTranslator_toEntity extends Test_AccountingTranslator {

    @Test
    @DisplayName("Happy Path: Translates ExpenseDeductionRequest to ExpenseDeduction entity with trimmed description")
    void shouldMapToEntity() {
        ExpenseDeductionRequest request = ExpenseDeductionRequest.builder()
                .description("  Annual D23 Membership  ")
                .category(ExpenseCategory.PROFESSIONAL_DUES_AND_FEES)
                .amountCents(9999L)
                .expenseDate(LocalDate.of(2026, 5, 10))
                .receiptDocumentUrl("https://vault.com/d23.pdf")
                .build();

        ExpenseDeduction entity = translator.toEntity(request, "agent-kelley");

        assertThat(entity.getAgentId()).isEqualTo("agent-kelley");
        assertThat(entity.getExpenseDescription()).isEqualTo("Annual D23 Membership");
        assertThat(entity.getCategory()).isEqualTo(ExpenseCategory.PROFESSIONAL_DUES_AND_FEES);
        assertThat(entity.getAmountCents()).isEqualTo(9999L);
        assertThat(entity.getExpenseDate()).isEqualTo(LocalDate.of(2026, 5, 10));
        assertThat(entity.getReceiptDocumentUrl()).isEqualTo("https://vault.com/d23.pdf");
    }
}