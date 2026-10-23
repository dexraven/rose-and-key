package com.goldgell.roseandkey.domain.marketing;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelWindowForecast {
    private SupplierCategory destination;
    private LocalDate windowStart;
    private LocalDate windowEnd;
    private int crowdScore; // 1 (lowest) to 10 (peak holiday)
    private String priceRating; // "VALUE", "MODERATE", "PEAK"
    private double historicalDiscountProbability; // e.g. 0.85
    private List<String> strategicNotes; // e.g. "Post-race lull", "Epcot Festival kickoff"
}