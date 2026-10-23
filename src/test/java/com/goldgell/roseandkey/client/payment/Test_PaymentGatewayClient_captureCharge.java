package com.goldgell.roseandkey.client.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class Test_PaymentGatewayClient_captureCharge extends Test_PaymentGatewayClient {

    @Test
    @DisplayName("Happy Path: Validates capture amount and confirms charge capture")
    void shouldCaptureChargeSuccessfully() {
        String authId = "ch_mock_auth_99";
        long amountCents = 75000L;

        doNothing().when(validator).validateAmount(amountCents);

        PaymentGatewayClient.PaymentGatewayResponse response =
                paymentGatewayClient.captureCharge(authId, amountCents);

        assertThat(response.successful()).isTrue();
        assertThat(response.transactionId()).isEqualTo(authId);
        assertThat(response.authorizationCode()).isEqualTo("CAPTURED");
        assertThat(response.gatewayMetadata()).containsEntry("capturedAmountCents", "75000");
        verify(validator).validateAmount(amountCents);
    }

    @Test
    @DisplayName("Negative Scenario: Blank or null authorizationId throws IllegalArgumentException")
    void shouldThrowWhenAuthIdIsBlank() {
        assertThatThrownBy(() -> paymentGatewayClient.captureCharge(null, 5000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Authorization ID cannot be null or blank");

        assertThatThrownBy(() -> paymentGatewayClient.captureCharge("   ", 5000L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Authorization ID cannot be null or blank");
    }
}