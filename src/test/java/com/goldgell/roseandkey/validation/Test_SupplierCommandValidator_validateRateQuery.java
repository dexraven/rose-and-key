package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_SupplierCommandValidator_validateRateQuery extends Test_SupplierCommandValidator {

    @Test
    @DisplayName("Happy Path: Accepts valid future check-in and check-out dates")
    void shouldPassValidQuery() {
        SupplierRateQueryRequest query = createValidRateQuery();

        assertThatCode(() -> validator.validateRateQuery(query))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Negative Scenario: Rejects query when check-out precedes check-in")
    void shouldRejectCheckOutBeforeCheckIn() {
        LocalDate checkIn = LocalDate.now().plusMonths(2);
        SupplierRateQueryRequest query = createValidRateQuery();
        query.setCheckInDate(checkIn);
        query.setCheckOutDate(checkIn.minusDays(1));

        assertThatThrownBy(() -> validator.validateRateQuery(query))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Check-out date cannot precede check-in date");
    }

    @Test
    @DisplayName("Negative Scenario: Rejects query when check-in is in the past")
    void shouldRejectPastCheckInDate() {
        SupplierRateQueryRequest query = createValidRateQuery();
        query.setCheckInDate(LocalDate.now().minusDays(1));
        query.setCheckOutDate(LocalDate.now().plusDays(3));

        assertThatThrownBy(() -> validator.validateRateQuery(query))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Check-in date cannot be in the past");
    }
}