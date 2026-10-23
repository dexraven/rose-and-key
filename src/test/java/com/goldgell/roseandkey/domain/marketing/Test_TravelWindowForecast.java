package com.goldgell.roseandkey.domain.marketing;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_TravelWindowForecast {

    @Test
    @DisplayName("POJO: Tests getters, setters, builder, and mutations")
    void testPojoAndMutations() {
        LocalDate start = LocalDate.of(2027, 1, 18);
        LocalDate end = LocalDate.of(2027, 2, 6);

        TravelWindowForecast forecast = new TravelWindowForecast();
        forecast.setDestination(SupplierCategory.DISNEY);
        forecast.setWindowStart(start);
        forecast.setWindowEnd(end);
        forecast.setCrowdScore(3);
        forecast.setPriceRating("VALUE");
        forecast.setHistoricalDiscountProbability(0.85);
        forecast.setStrategicNotes(List.of("Low waits"));

        assertThat(forecast.getDestination()).isEqualTo(SupplierCategory.DISNEY);
        assertThat(forecast.getWindowStart()).isEqualTo(start);
        assertThat(forecast.getWindowEnd()).isEqualTo(end);
        assertThat(forecast.getCrowdScore()).isEqualTo(3);
        assertThat(forecast.getPriceRating()).isEqualTo("VALUE");
        assertThat(forecast.getHistoricalDiscountProbability()).isEqualTo(0.85);
        assertThat(forecast.getStrategicNotes()).containsExactly("Low waits");

        TravelWindowForecast built = TravelWindowForecast.builder()
                .destination(SupplierCategory.UNIVERSAL)
                .crowdScore(4)
                .build();

        assertThat(built.getDestination()).isEqualTo(SupplierCategory.UNIVERSAL);
        assertThat(built.getCrowdScore()).isEqualTo(4);
    }
}