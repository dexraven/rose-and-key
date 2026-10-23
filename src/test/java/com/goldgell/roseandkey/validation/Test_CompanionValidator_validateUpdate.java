package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.CompanionUpdateRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_CompanionValidator_validateUpdate extends Test_CompanionValidator {

    @Test
    @DisplayName("Happy Path: Accepts companion with realistic past date of birth")
    void shouldPassValidDateOfBirth() {
        CompanionUpdateRequest request = createRequestWithDob(LocalDate.of(1990, 5, 20));

        assertThatCode(() -> validator.validateUpdate(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Negative Scenario: Rejects companion with future date of birth")
    void shouldRejectFutureDateOfBirth() {
        CompanionUpdateRequest request = createRequestWithDob(LocalDate.now().plusDays(1));

        assertThatThrownBy(() -> validator.validateUpdate(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be in the future");
    }

    @Test
    @DisplayName("Negative Scenario: Rejects companion date of birth exceeding 120-year threshold")
    void shouldRejectExceedingMaximumAgeThreshold() {
        CompanionUpdateRequest request = createRequestWithDob(LocalDate.now().minusYears(121));

        assertThatThrownBy(() -> validator.validateUpdate(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exceeds maximum reasonable threshold");
    }
}