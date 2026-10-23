package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_TripIntakeValidator_validateSubmission extends Test_TripIntakeValidator {

    @Test
    @DisplayName("Happy Path: Accepts valid future dates and matching headcount")
    void shouldPassValidSubmission() {
        TripIntakeSubmissionRequest request = createBaseRequestBuilder().build();
        assertThatCode(() -> validator.validateSubmission(request)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Negative Scenario: Rejects when return precedes departure")
    void shouldRejectWhenReturnPrecedesDeparture() {
        LocalDate departure = LocalDate.now().plusMonths(2);
        TripIntakeSubmissionRequest request = createBaseRequestBuilder()
                .earliestDeparture(departure)
                .latestReturn(departure.minusDays(1))
                .build();

        assertThatThrownBy(() -> validator.validateSubmission(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Latest return date cannot precede earliest departure date.");
    }

    @Test
    @DisplayName("Negative Scenario: Rejects when earliest departure is in the past")
    void shouldRejectPastDeparture() {
        LocalDate pastDeparture = LocalDate.now().minusDays(1);
        TripIntakeSubmissionRequest request = createBaseRequestBuilder()
                .earliestDeparture(pastDeparture)
                .latestReturn(pastDeparture.plusDays(5))
                .build();

        assertThatThrownBy(() -> validator.validateSubmission(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Earliest departure date cannot be in the past.");
    }

    @Test
    @DisplayName("Negative Scenario: Rejects when headcount is less than total party size")
    void shouldRejectHeadcountLessThanTravelersList() {
        TripIntakeSubmissionRequest request = createBaseRequestBuilder()
                .partyHeadcount(1) // 1 lead + 1 companion = 2 travelers
                .build();

        assertThatThrownBy(() -> validator.validateSubmission(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be smaller than registered travelers list");
    }
}