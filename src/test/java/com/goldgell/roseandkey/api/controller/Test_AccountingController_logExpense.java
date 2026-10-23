package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import com.goldgell.roseandkey.api.response.ExpenseDeductionResponse;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_AccountingController_logExpense extends Test_AccountingController {

    @Test
    @DisplayName("Happy Path: POST /api/v1/accounting/expenses returns 201 Created on valid deduction")
    void shouldLogExpenseSuccessfully() throws Exception {
        UUID expenseId = UUID.randomUUID();
        ExpenseDeductionRequest request = ExpenseDeductionRequest.builder()
                .description("Host Agency Annual Fee")
                .category(ExpenseCategory.PROFESSIONAL_DUES_AND_FEES)
                .amountCents(39500L)
                .expenseDate(LocalDate.now().minusDays(5))
                .build();

        ExpenseDeductionResponse response = ExpenseDeductionResponse.builder()
                .id(expenseId)
                .agentId("agent-kelley-default")
                .description("Host Agency Annual Fee")
                .category(ExpenseCategory.PROFESSIONAL_DUES_AND_FEES)
                .amountCents(39500L)
                .expenseDate(LocalDate.now().minusDays(5))
                .build();

        when(accountingService.logExpenseDeduction(any(), eq("agent-kelley-default"))).thenReturn(response);

        mockMvc.perform(post("/api/v1/accounting/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expenseId.toString()))
                .andExpect(jsonPath("$.amountCents").value(39500L))
                .andExpect(jsonPath("$.category").value("PROFESSIONAL_DUES_AND_FEES"));
    }

    @Test
    @DisplayName("Negative Scenario: POST /api/v1/accounting/expenses returns 400 Bad Request on invalid fields")
    void shouldReturn400OnValidationFailure() throws Exception {
        ExpenseDeductionRequest invalidRequest = ExpenseDeductionRequest.builder()
                .description("") // Blank
                .category(null)  // Required
                .amountCents(-50L) // Negative
                .expenseDate(null)
                .build();

        mockMvc.perform(post("/api/v1/accounting/expenses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}