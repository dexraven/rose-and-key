package com.goldgell.roseandkey.api.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class TaxEstimateReportResponse {
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