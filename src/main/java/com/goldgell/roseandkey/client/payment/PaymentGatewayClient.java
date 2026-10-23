package com.goldgell.roseandkey.client.payment;

import java.util.Map;

public interface PaymentGatewayClient {

    /**
     * Authorizes a hold or charge against a PCI-compliant vaulted token.
     */
    PaymentGatewayResponse authorizeCharge(String vaultedPaymentToken, long amountCents, String currency, String idempotencyKey);

    /**
     * Captures a previously authorized charge.
     */
    PaymentGatewayResponse captureCharge(String authorizationId, long amountCents);

    /**
     * Voids or refunds a transaction.
     */
    PaymentGatewayResponse refundCharge(String transactionId, long amountCents, String reason);

    record PaymentGatewayResponse(
            boolean successful,
            String transactionId,
            String authorizationCode,
            String errorCode,
            String errorMessage,
            Map<String, String> gatewayMetadata
    ) {}
}