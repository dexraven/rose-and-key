package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.response.TaxEstimateReportResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_AccountingController_getQuarterlyTaxEstimate extends Test_AccountingController {

    @Test
    @DisplayName("Happy Path: GET /api/v1/accounting/taxes/quarterly returns 200 OK with tax calculation report")
    void shouldReturnQuarterlyTaxEstimate() throws Exception {
        TaxEstimateReportResponse report = TaxEstimateReportResponse.builder()
                .taxYear(2026)
                .quarter(3)
                .grossCommissionEarningsCents(1000000L)
                .netAdvisorPayoutCents(800000L)
                .totalDeductibleExpensesCents(200000L)
                .taxableSelfEmploymentNetCents(600000L)
                .estimatedSelfEmploymentTaxCents(84766L)
                .estimatedFederalIncomeTaxCents(72000L)
                .totalQuarterlySetAsideRecommendationCents(156766L)
                .build();

        when(accountingService.computeQuarterlyTaxEstimate("agent-kelley-default", 2026, 3)).thenReturn(report);

        mockMvc.perform(get("/api/v1/accounting/taxes/quarterly?year=2026&quarter=3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taxYear").value(2026))
                .andExpect(jsonPath("$.quarter").value(3))
                .andExpect(jsonPath("$.netAdvisorPayoutCents").value(800000L))
                .andExpect(jsonPath("$.totalQuarterlySetAsideRecommendationCents").value(156766L));
    }

    @Test
    @DisplayName("Negative Scenario: GET /api/v1/accounting/taxes/quarterly returns 400 Bad Request on invalid quarter")
    void shouldReturn400OnInvalidQuarter() throws Exception {
        when(accountingService.computeQuarterlyTaxEstimate("agent-kelley-default", 2026, 5))
                .thenThrow(new IllegalArgumentException("Tax quarter must be between 1 and 4."));

        mockMvc.perform(get("/api/v1/accounting/taxes/quarterly?year=2026&quarter=5"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Tax quarter must be between 1 and 4."));
    }
}