package com.goldgell.roseandkey.service.accounting;

import com.goldgell.roseandkey.api.response.TaxEstimateReportResponse;
import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class Test_TravelAgencyAccountingService_computeQuarterlyTaxEstimate extends Test_TravelAgencyAccountingService {

    @Test
    @DisplayName("Happy Path: Correctly computes self-employment net and federal reserve escrow")
    void shouldComputeTaxEstimates() {
        doNothing().when(accountingValidator).validateQuarter(1);

        ExpenseDeduction deduction = ExpenseDeduction.builder().amountCents(100000L).build(); // $1,000 expense
        CommissionLedgerEntry paidComm = CommissionLedgerEntry.builder()
                .grossCommissionCents(500000L)
                .netAdvisorPayoutCents(400000L) // $4,000 net payout
                .build();

        when(expenseDeductionRepository.findByAgentIdAndExpenseDateBetween(eq("agent-1"), any(), any()))
                .thenReturn(List.of(deduction));
        when(commissionLedgerRepository.findByStatus(CommissionLedgerEntry.PayoutStatus.DISBURSED_TO_ADVISOR))
                .thenReturn(List.of(paidComm));

        TaxEstimateReportResponse report = accountingService.computeQuarterlyTaxEstimate("agent-1", 2026, 1);

        assertThat(report.getNetAdvisorPayoutCents()).isEqualTo(400000L);
        assertThat(report.getTotalDeductibleExpensesCents()).isEqualTo(100000L);
        assertThat(report.getTaxableSelfEmploymentNetCents()).isEqualTo(300000L); // $3,000 net taxable
        assertThat(report.getTotalQuarterlySetAsideRecommendationCents()).isGreaterThan(0L);
    }
}