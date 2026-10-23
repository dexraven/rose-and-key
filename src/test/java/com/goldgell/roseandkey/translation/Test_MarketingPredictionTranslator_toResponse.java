package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.VacationPredictionResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.domain.marketing.TravelWindowForecast;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Test_MarketingPredictionTranslator_toResponse extends Test_MarketingPredictionTranslator {

    @Test
    @DisplayName("Happy Path: Maps TravelWindowForecast to VacationPredictionResponse")
    void shouldMapToResponse() {
        TravelWindowForecast forecast = TravelWindowForecast.builder()
                .destination(SupplierCategory.DISNEY)
                .windowStart(LocalDate.of(2027, 1, 18))
                .windowEnd(LocalDate.of(2027, 2, 6))
                .crowdScore(3)
                .priceRating("VALUE")
                .historicalDiscountProbability(0.85)
                .strategicNotes(List.of("Low waits"))
                .build();

        VacationPredictionResponse response = translator.toResponse(forecast);

        assertThat(response.getDestination()).isEqualTo(SupplierCategory.DISNEY);
        assertThat(response.getWindowStart()).isEqualTo(LocalDate.of(2027, 1, 18));
        assertThat(response.getCrowdScore()).isEqualTo(3);
        assertThat(response.getPriceRating()).isEqualTo("VALUE");
        assertThat(response.getHistoricalDiscountProbability()).isEqualTo(0.85);
        assertThat(response.getStrategicNotes()).containsExactly("Low waits");
    }
}