package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_ExpenseDeductionResponse {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, and getters")
    void testValueContract() {
        UUID id = UUID.randomUUID();
        LocalDate now = LocalDate.now();

        ExpenseDeductionResponse res1 = ExpenseDeductionResponse.builder()
                .id(id)
                .agentId("agent-01")
                .description("Client Gift Basket")
                .category(ExpenseCategory.CLIENT_GIFTS_AND_PROMOS)
                .amountCents(7500L)
                .expenseDate(now)
                .receiptDocumentUrl("https://receipts.com/2")
                .build();

        ExpenseDeductionResponse res2 = ExpenseDeductionResponse.builder()
                .id(id)
                .agentId("agent-01")
                .description("Client Gift Basket")
                .category(ExpenseCategory.CLIENT_GIFTS_AND_PROMOS)
                .amountCents(7500L)
                .expenseDate(now)
                .receiptDocumentUrl("https://receipts.com/2")
                .build();

        assertThat(res1).isEqualTo(res2);
        assertThat(res1.hashCode()).isEqualTo(res2.hashCode());
        assertThat(res1.getId()).isEqualTo(id);
        assertThat(res1.getAmountCents()).isEqualTo(7500L);
    }
}