package com.goldgell.roseandkey.api.response;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_TaxEstimateReportResponse {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, and getters")
    void testValueContract() {
        TaxEstimateReportResponse res1 = TaxEstimateReportResponse.builder()
                .taxYear(2026)
                .quarter(2)
                .grossCommissionEarningsCents(500000L)
                .netAdvisorPayoutCents(400000L)
                .totalDeductibleExpensesCents(50000L)
                .taxableSelfEmploymentNetCents(350000L)
                .estimatedSelfEmploymentTaxCents(49447L)
                .estimatedFederalIncomeTaxCents(42000L)
                .totalQuarterlySetAsideRecommendationCents(91447L)
                .build();

        TaxEstimateReportResponse res2 = TaxEstimateReportResponse.builder()
                .taxYear(2026)
                .quarter(2)
                .grossCommissionEarningsCents(500000L)
                .netAdvisorPayoutCents(400000L)
                .totalDeductibleExpensesCents(50000L)
                .taxableSelfEmploymentNetCents(350000L)
                .estimatedSelfEmploymentTaxCents(49447L)
                .estimatedFederalIncomeTaxCents(42000L)
                .totalQuarterlySetAsideRecommendationCents(91447L)
                .build();

        assertThat(res1).isEqualTo(res2);
        assertThat(res1.hashCode()).isEqualTo(res2.hashCode());
        assertThat(res1.getQuarter()).isEqualTo(2);
        assertThat(res1.getTotalQuarterlySetAsideRecommendationCents()).isEqualTo(91447L);
    }
}