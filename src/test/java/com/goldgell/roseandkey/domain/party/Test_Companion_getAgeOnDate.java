package com.goldgell.roseandkey.domain.party;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_Companion_getAgeOnDate extends Test_Companion {

    @Test
    @DisplayName("Happy Path: Correctly computes chronological age on target travel date")
    void shouldCalculateCorrectAge() {
        Companion companion = createDefaultCompanion(); // DOB: 2014-06-15
        LocalDate travelDate = LocalDate.of(2026, 12, 19);

        assertThat(companion.getAgeOnDate(travelDate)).isEqualTo(12);
    }

    @Test
    @DisplayName("Boundary: Exactly on birthday vs. one day prior")
    void shouldHandleBirthdayBoundaries() {
        Companion companion = createDefaultCompanion(); // DOB: 2014-06-15

        LocalDate dayBefore18th = LocalDate.of(2032, 6, 14);
        LocalDate exactly18th = LocalDate.of(2032, 6, 15);

        assertThat(companion.getAgeOnDate(dayBefore18th)).isEqualTo(17);
        assertThat(companion.getAgeOnDate(exactly18th)).isEqualTo(18);
    }

    @Test
    @DisplayName("Negative Scenario: Passing null date triggers NullPointerException")
    void shouldThrowWhenDateIsNull() {
        Companion companion = createDefaultCompanion();

        assertThatThrownBy(() -> companion.getAgeOnDate(null))
                .isInstanceOf(NullPointerException.class);
    }
}