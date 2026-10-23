package com.goldgell.roseandkey.domain.accounting;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TaxEstimateReport {
    private int taxYear;
    private int quarter;
    private long grossCommissionEarningsCents;
    private long netAdvisorPayoutCents;
    private long totalDeductibleExpensesCents;
    private long taxableSelfEmploymentNetCents;
    private long estimatedSelfEmploymentTaxCents;
    private long estimatedFederalIncomeTaxCents;
    private long totalQuarterlySetAsideRecommendationCents;
}