package com.goldgell.roseandkey.client.payment;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class Test_PaymentGatewayClient_refundCharge extends Test_PaymentGatewayClient {

    @Test
    @DisplayName("Happy Path: Processes refund with default reason fallback if none provided")
    void shouldRefundChargeSuccessfully() {
        String txId = "ch_mock_tx_123";
        long refundCents = 20000L;

        doNothing().when(validator).validateAmount(refundCents);

        PaymentGatewayClient.PaymentGatewayResponse response =
                paymentGatewayClient.refundCharge(txId, refundCents, null);

        assertThat(response.successful()).isTrue();
        assertThat(response.transactionId()).startsWith("re_");
        assertThat(response.authorizationCode()).isEqualTo("REFUNDED");
        assertThat(response.gatewayMetadata()).containsEntry("refundedCents", "20000");
        assertThat(response.gatewayMetadata()).containsEntry("reason", "CUSTOMER_REQUEST");
        verify(validator).validateAmount(refundCents);
    }

    @Test
    @DisplayName("Negative Scenario: Blank transactionId throws IllegalArgumentException")
    void shouldThrowWhenTransactionIdIsBlank() {
        assertThatThrownBy(() -> paymentGatewayClient.refundCharge("", 5000L, "Duplicate"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Transaction ID cannot be null or blank");
    }
}