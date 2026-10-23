package com.goldgell.roseandkey.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_PaymentTransactionValidator_validatePaymentAuthorization extends Test_PaymentTransactionValidator {

    @Test
    @DisplayName("Happy Path: Accepts valid token, positive amount, and standard ISO currency")
    void shouldPassValidAuthorization() {
        assertThatCode(() -> validator.validatePaymentAuthorization("pm_card_visa_tok123", 50000L, "USD"))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("PCI Violation Negative Scenario: Rejects raw credit card number passed as token")
    void shouldRejectRawCardNumber() {
        assertThatThrownBy(() -> validator.validatePaymentAuthorization("4111111111111111", 50000L, "USD"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("PCI Violation: Raw cardholder numbers cannot be processed");
    }

    @Test
    @DisplayName("Negative Scenario: Rejects zero or negative amount")
    void shouldRejectZeroOrNegativeAmount() {
        assertThatThrownBy(() -> validator.validatePaymentAuthorization("pm_valid_tok", 0L, "USD"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be strictly greater than zero cents");

        assertThatThrownBy(() -> validator.validatePaymentAuthorization("pm_valid_tok", -100L, "USD"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must be strictly greater than zero cents");
    }

    @Test
    @DisplayName("Boundary Scenario: Rejects amount exceeding $250,000 threshold")
    void shouldRejectAmountExceedingCeiling() {
        assertThatThrownBy(() -> validator.validatePaymentAuthorization("pm_valid_tok", 25_000_001L, "USD"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exceeds single-charge authorized ceiling");
    }

    @Test
    @DisplayName("Negative Scenario: Rejects invalid ISO currency codes")
    void shouldRejectInvalidCurrency() {
        assertThatThrownBy(() -> validator.validatePaymentAuthorization("pm_valid_tok", 1000L, "INVALID"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("3-letter ISO-4217");

        assertThatThrownBy(() -> validator.validatePaymentAuthorization("pm_valid_tok", 1000L, "XYZ"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unsupported or invalid currency code");
    }
}