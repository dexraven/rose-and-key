package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class VacationPredictionResponse {
    private SupplierCategory destination;
    private LocalDate windowStart;
    private LocalDate windowEnd;
    private int crowdScore;
    private String priceRating;
    private double historicalDiscountProbability;
    private List<String> strategicNotes;
}