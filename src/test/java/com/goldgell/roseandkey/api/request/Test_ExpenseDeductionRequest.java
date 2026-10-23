package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_ExpenseDeductionRequest {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, toString, and builder values")
    void testValueContract() {
        LocalDate now = LocalDate.now();
        ExpenseDeductionRequest req1 = ExpenseDeductionRequest.builder()
                .description("Park Hopper Tickets for FAM tour")
                .category(ExpenseCategory.ANNUAL_PASS_AND_PARK_TICKETS)
                .amountCents(35000L)
                .expenseDate(now)
                .receiptDocumentUrl("https://receipts.com/1")
                .build();

        ExpenseDeductionRequest req2 = ExpenseDeductionRequest.builder()
                .description("Park Hopper Tickets for FAM tour")
                .category(ExpenseCategory.ANNUAL_PASS_AND_PARK_TICKETS)
                .amountCents(35000L)
                .expenseDate(now)
                .receiptDocumentUrl("https://receipts.com/1")
                .build();

        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
        assertThat(req1.getAmountCents()).isEqualTo(35000L);
        assertThat(req1.getDescription()).isEqualTo("Park Hopper Tickets for FAM tour");
        assertThat(req1.getReceiptDocumentUrl()).isEqualTo("https://receipts.com/1");
    }
}