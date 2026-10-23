package com.goldgell.roseandkey.service.accounting;

import com.goldgell.roseandkey.api.response.TaxEstimateReportResponse;
import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class Test_TravelAgencyAccountingService_computeQuarterlyTaxEstimate_boundaries extends Test_TravelAgencyAccountingService {

    @Test
    @DisplayName("Boundary: When expenses exceed net payout, taxable net is 0 and tax recommendation is 0")
    void shouldHandleDeductionsExceedingIncome() {
        doNothing().when(accountingValidator).validateQuarter(2);

        ExpenseDeduction bigExpense = ExpenseDeduction.builder().amountCents(500000L).build(); // $5,000
        CommissionLedgerEntry smallPayout = CommissionLedgerEntry.builder()
                .grossCommissionCents(200000L)
                .netAdvisorPayoutCents(160000L) // $1,600
                .build();

        when(expenseDeductionRepository.findByAgentIdAndExpenseDateBetween(eq("agent-1"), any(), any()))
                .thenReturn(List.of(bigExpense));
        when(commissionLedgerRepository.findByStatus(CommissionLedgerEntry.PayoutStatus.DISBURSED_TO_ADVISOR))
                .thenReturn(List.of(smallPayout));

        TaxEstimateReportResponse report = accountingService.computeQuarterlyTaxEstimate("agent-1", 2026, 2);

        assertThat(report.getTaxableSelfEmploymentNetCents()).isEqualTo(0L);
        assertThat(report.getEstimatedSelfEmploymentTaxCents()).isEqualTo(0L);
        assertThat(report.getEstimatedFederalIncomeTaxCents()).isEqualTo(0L);
        assertThat(report.getTotalQuarterlySetAsideRecommendationCents()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Boundary: When agent has zero expenses and zero payouts, returns all zeroes safely")
    void shouldHandleZeroActivityQuarter() {
        doNothing().when(accountingValidator).validateQuarter(4);

        when(expenseDeductionRepository.findByAgentIdAndExpenseDateBetween(eq("agent-1"), any(), any()))
                .thenReturn(Collections.emptyList());
        when(commissionLedgerRepository.findByStatus(CommissionLedgerEntry.PayoutStatus.DISBURSED_TO_ADVISOR))
                .thenReturn(Collections.emptyList());

        TaxEstimateReportResponse report = accountingService.computeQuarterlyTaxEstimate("agent-1", 2026, 4);

        assertThat(report.getGrossCommissionEarningsCents()).isEqualTo(0L);
        assertThat(report.getNetAdvisorPayoutCents()).isEqualTo(0L);
        assertThat(report.getTotalDeductibleExpensesCents()).isEqualTo(0L);
        assertThat(report.getTotalQuarterlySetAsideRecommendationCents()).isEqualTo(0L);
    }
}