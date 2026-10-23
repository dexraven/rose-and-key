package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_SupplierCommandValidator_validateLockRequest extends Test_SupplierCommandValidator {

    @Test
    @DisplayName("Happy Path: Accepts lock request when total equals subtotal + taxes")
    void shouldPassValidLockRequest() {
        LockReservationRequest request = createValidLockRequest(100000L, 15000L, 115000L);

        assertThatCode(() -> validator.validateLockRequest(request))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("Negative Scenario: Rejects lock request when arithmetic totals do not balance")
    void shouldRejectMismatchedTotals() {
        LockReservationRequest request = createValidLockRequest(100000L, 15000L, 120000L);

        assertThatThrownBy(() -> validator.validateLockRequest(request))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Total cents must equal subtotal plus taxes and fees");
    }
}