package com.goldgell.roseandkey.service.accounting;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import com.goldgell.roseandkey.api.response.ExpenseDeductionResponse;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class Test_TravelAgencyAccountingService_logExpenseDeduction extends Test_TravelAgencyAccountingService {

    @Test
    @DisplayName("Happy Path: Validates, translates, saves, and returns ExpenseDeductionResponse")
    void shouldLogExpenseDeductionSuccessfully() {
        ExpenseDeductionRequest request = ExpenseDeductionRequest.builder()
                .description("Printer Ink & Intake Folders")
                .category(ExpenseCategory.OFFICE_SUPPLIES)
                .amountCents(6500L)
                .expenseDate(LocalDate.now().minusDays(1))
                .build();

        ExpenseDeduction entity = ExpenseDeduction.builder().id(UUID.randomUUID()).build();
        ExpenseDeductionResponse expectedResponse = ExpenseDeductionResponse.builder()
                .id(entity.getId())
                .amountCents(6500L)
                .build();

        doNothing().when(accountingValidator).validateExpenseDeduction(request);
        when(accountingTranslator.toEntity(request, "agent-kelley")).thenReturn(entity);
        when(expenseDeductionRepository.save(entity)).thenReturn(entity);
        when(accountingTranslator.toResponse(entity)).thenReturn(expectedResponse);

        ExpenseDeductionResponse response = accountingService.logExpenseDeduction(request, "agent-kelley");

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(entity.getId());
        verify(accountingValidator).validateExpenseDeduction(request);
        verify(expenseDeductionRepository).save(entity);
    }
}