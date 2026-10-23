package com.goldgell.roseandkey.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_AccountingValidator_validateQuarter extends Test_AccountingValidator {

    @Test
    @DisplayName("Happy Path: Accepts valid quarters 1 through 4")
    void shouldAcceptValidQuarters() {
        assertThatCode(() -> validator.validateQuarter(1)).doesNotThrowAnyException();
        assertThatCode(() -> validator.validateQuarter(4)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Negative Scenario: Rejects quarter out of bounds")
    void shouldRejectOutOfBoundsQuarters() {
        assertThatThrownBy(() -> validator.validateQuarter(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tax quarter must be between 1 and 4");

        assertThatThrownBy(() -> validator.validateQuarter(5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Tax quarter must be between 1 and 4");
    }
}