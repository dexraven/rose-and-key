package com.goldgell.roseandkey.service.marketing;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.domain.marketing.TravelWindowForecast;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_PredictiveVacationEngine_forecastBestTravelWindows extends Test_PredictiveVacationEngine {

    @Test
    @DisplayName("Happy Path: Returns historical windows for Disney")
    void shouldReturnDisneyTravelWindows() {
        List<TravelWindowForecast> windows = engine.forecastBestTravelWindows(SupplierCategory.DISNEY, 2027);

        assertThat(windows).hasSize(3);
        assertThat(windows).anyMatch(w -> w.getPriceRating().equals("VALUE") && w.getCrowdScore() <= 3);
        assertThat(windows).anyMatch(w -> w.getStrategicNotes().contains("Post-marathon seasonal room promotions active"));
    }

    @Test
    @DisplayName("Happy Path: Returns historical windows for Universal")
    void shouldReturnUniversalTravelWindows() {
        List<TravelWindowForecast> windows = engine.forecastBestTravelWindows(SupplierCategory.UNIVERSAL, 2027);

        assertThat(windows).hasSize(2);
        assertThat(windows).anyMatch(w -> w.getStrategicNotes().contains("HHN off-peak weeknight rates"));
    }

    @Test
    @DisplayName("Happy Path: Returns historical windows for Marriott")
    void shouldReturnMarriottTravelWindows() {
        List<TravelWindowForecast> windows = engine.forecastBestTravelWindows(SupplierCategory.MARRIOTT, 2027);

        assertThat(windows).hasSize(1);
        assertThat(windows.getFirst().getStrategicNotes()).contains("FAM travel advisor rates active");
    }

    @Test
    @DisplayName("Happy Path: Returns empty list for categories without forecasts (e.g. CRUISE)")
    void shouldReturnEmptyListForUnsupportedCategories() {
        List<TravelWindowForecast> windows = engine.forecastBestTravelWindows(SupplierCategory.CRUISE, 2027);
        assertThat(windows).isEmpty();
    }

    @Test
    @DisplayName("Negative Scenario: Rejects null destination category")
    void shouldThrowWhenDestinationIsNull() {
        assertThatThrownBy(() -> engine.forecastBestTravelWindows(null, 2027))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Destination category cannot be null");
    }
}