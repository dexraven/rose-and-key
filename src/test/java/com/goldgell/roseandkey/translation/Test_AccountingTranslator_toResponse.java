package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.ExpenseDeductionResponse;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_AccountingTranslator_toResponse extends Test_AccountingTranslator {

    @Test
    @DisplayName("Happy Path: Translates ExpenseDeduction entity to ExpenseDeductionResponse DTO")
    void shouldMapToResponse() {
        UUID id = UUID.randomUUID();
        ExpenseDeduction entity = ExpenseDeduction.builder()
                .id(id)
                .agentId("agent-kelley")
                .expenseDescription("Stateroom Wine Basket for Client")
                .category(ExpenseCategory.CLIENT_GIFTS_AND_PROMOS)
                .amountCents(8500L)
                .expenseDate(LocalDate.of(2026, 6, 1))
                .receiptDocumentUrl("https://vault.com/wine.pdf")
                .build();

        ExpenseDeductionResponse response = translator.toResponse(entity);

        assertThat(response.getId()).isEqualTo(id);
        assertThat(response.getAgentId()).isEqualTo("agent-kelley");
        assertThat(response.getDescription()).isEqualTo("Stateroom Wine Basket for Client");
        assertThat(response.getCategory()).isEqualTo(ExpenseCategory.CLIENT_GIFTS_AND_PROMOS);
        assertThat(response.getAmountCents()).isEqualTo(8500L);
    }
}