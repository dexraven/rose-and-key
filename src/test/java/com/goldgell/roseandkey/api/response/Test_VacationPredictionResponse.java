package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_VacationPredictionResponse {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, and getters")
    void testValueContract() {
        LocalDate start = LocalDate.of(2027, 1, 18);
        LocalDate end = LocalDate.of(2027, 2, 6);

        VacationPredictionResponse res1 = VacationPredictionResponse.builder()
                .destination(SupplierCategory.DISNEY)
                .windowStart(start)
                .windowEnd(end)
                .crowdScore(3)
                .priceRating("VALUE")
                .historicalDiscountProbability(0.85)
                .strategicNotes(List.of("Low waits"))
                .build();

        VacationPredictionResponse res2 = VacationPredictionResponse.builder()
                .destination(SupplierCategory.DISNEY)
                .windowStart(start)
                .windowEnd(end)
                .crowdScore(3)
                .priceRating("VALUE")
                .historicalDiscountProbability(0.85)
                .strategicNotes(List.of("Low waits"))
                .build();

        assertThat(res1).isEqualTo(res2);
        assertThat(res1.hashCode()).isEqualTo(res2.hashCode());
        assertThat(res1.getCrowdScore()).isEqualTo(3);
        assertThat(res1.getPriceRating()).isEqualTo("VALUE");
    }
}