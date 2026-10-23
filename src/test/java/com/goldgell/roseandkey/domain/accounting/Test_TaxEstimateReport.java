package com.goldgell.roseandkey.domain.accounting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_TaxEstimateReport {

    @Test
    @DisplayName("POJO & Contracts: Verifies getters, setters, builder, equals, and hashCode")
    void testPojoAndContracts() {
        TaxEstimateReport report1 = TaxEstimateReport.builder()
                .taxYear(2026)
                .quarter(3)
                .grossCommissionEarningsCents(1000000L)
                .netAdvisorPayoutCents(800000L)
                .totalDeductibleExpensesCents(150000L)
                .taxableSelfEmploymentNetCents(650000L)
                .estimatedSelfEmploymentTaxCents(91830L)
                .estimatedFederalIncomeTaxCents(78000L)
                .totalQuarterlySetAsideRecommendationCents(169830L)
                .build();

        TaxEstimateReport report2 = TaxEstimateReport.builder()
                .taxYear(2026)
                .quarter(3)
                .grossCommissionEarningsCents(1000000L)
                .netAdvisorPayoutCents(800000L)
                .totalDeductibleExpensesCents(150000L)
                .taxableSelfEmploymentNetCents(650000L)
                .estimatedSelfEmploymentTaxCents(91830L)
                .estimatedFederalIncomeTaxCents(78000L)
                .totalQuarterlySetAsideRecommendationCents(169830L)
                .build();

        TaxEstimateReport differentReport = TaxEstimateReport.builder()
                .taxYear(2026)
                .quarter(4)
                .build();

        assertThat(report1).isEqualTo(report2);
        assertThat(report1.hashCode()).isEqualTo(report2.hashCode());
        assertThat(report1).isNotEqualTo(differentReport);
        assertThat(report1.toString()).contains("taxYear=2026", "quarter=3", "169830");

        TaxEstimateReport mutable = new TaxEstimateReport();
        mutable.setTaxYear(2027);
        mutable.setQuarter(1);
        mutable.setGrossCommissionEarningsCents(500000L);
        mutable.setNetAdvisorPayoutCents(400000L);
        mutable.setTotalDeductibleExpensesCents(50000L);
        mutable.setTaxableSelfEmploymentNetCents(350000L);
        mutable.setEstimatedSelfEmploymentTaxCents(49447L);
        mutable.setEstimatedFederalIncomeTaxCents(42000L);
        mutable.setTotalQuarterlySetAsideRecommendationCents(91447L);

        assertThat(mutable.getTaxYear()).isEqualTo(2027);
        assertThat(mutable.getQuarter()).isEqualTo(1);
        assertThat(mutable.getTaxableSelfEmploymentNetCents()).isEqualTo(350000L);
    }
}