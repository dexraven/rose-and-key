package com.goldgell.roseandkey.client.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class Test_PaymentGatewayClient_authorizeCharge extends Test_PaymentGatewayClient {

    @Test
    @DisplayName("Happy Path: Validates authorization, dispatches to gateway, and returns successful transaction response")
    void shouldAuthorizeChargeSuccessfully() {
        String token = "pm_vault_tok_123";
        long amountCents = 150000L;
        String currency = "USD";
        String idempotencyKey = "idemp_test_key_001";

        doNothing().when(validator).validatePaymentAuthorization(token, amountCents, currency);

        PaymentGatewayClient.PaymentGatewayResponse response =
                paymentGatewayClient.authorizeCharge(token, amountCents, currency, idempotencyKey);

        assertThat(response).isNotNull();
        assertThat(response.successful()).isTrue();
        assertThat(response.transactionId()).startsWith("ch_mock_");
        assertThat(response.authorizationCode()).startsWith("AUTH_");
        assertThat(response.gatewayMetadata()).containsEntry("idempotencyKey", idempotencyKey);
        verify(validator).validatePaymentAuthorization(token, amountCents, currency);
    }

    @Test
    @DisplayName("Negative Scenario: Blank or null idempotencyKey throws IllegalArgumentException")
    void shouldThrowWhenIdempotencyKeyIsMissing() {
        String token = "pm_vault_tok_123";
        long amountCents = 5000L;
        String currency = "USD";

        doNothing().when(validator).validatePaymentAuthorization(token, amountCents, currency);

        assertThatThrownBy(() -> paymentGatewayClient.authorizeCharge(token, amountCents, currency, null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Idempotency key is required");

        assertThatThrownBy(() -> paymentGatewayClient.authorizeCharge(token, amountCents, currency, "   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Idempotency key is required");
    }

    @Test
    @DisplayName("Negative Scenario: Upstream validator exception bubbles directly")
    void shouldPropagateValidationException() {
        doThrow(new IllegalArgumentException("PCI Violation: Raw cardholder numbers cannot be processed."))
                .when(validator).validatePaymentAuthorization("4111111111111111", 5000L, "USD");

        assertThatThrownBy(() -> paymentGatewayClient.authorizeCharge("4111111111111111", 5000L, "USD", "idemp_1"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("PCI Violation");
    }
}