package com.goldgell.roseandkey.client.payment;

import com.goldgell.roseandkey.validation.PaymentTransactionValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class StripePaymentGatewayClient implements PaymentGatewayClient {

    private static final Logger log = LoggerFactory.getLogger(StripePaymentGatewayClient.class);

    private final RestClient restClient;
    private final PaymentTransactionValidator validator;

    @Override
    public PaymentGatewayResponse authorizeCharge(String vaultedPaymentToken, long amountCents, String currency, String idempotencyKey) {
        validator.validatePaymentAuthorization(vaultedPaymentToken, amountCents, currency);

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("Idempotency key is required for payment authorization.");
        }

        try {
            // Simulated gateway payload exchange via Spring 6 RestClient
            log.info("Dispatching authorization intent to payment gateway: token=[{}], amountCents=[{}], currency=[{}]",
                    vaultedPaymentToken, amountCents, currency);

            String txId = "ch_mock_" + UUID.randomUUID().toString().substring(0, 12);
            String authCode = "AUTH_" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            return new PaymentGatewayResponse(
                    true,
                    txId,
                    authCode,
                    null,
                    null,
                    Map.of("gateway", "stripe_v1", "idempotencyKey", idempotencyKey)
            );
        } catch (Exception e) {
            log.error("Payment authorization failed at gateway level", e);
            return new PaymentGatewayResponse(
                    false,
                    null,
                    null,
                    "GATEWAY_COMMUNICATION_ERROR",
                    e.getMessage(),
                    Map.of()
            );
        }
    }

    @Override
    public PaymentGatewayResponse captureCharge(String authorizationId, long amountCents) {
        if (authorizationId == null || authorizationId.isBlank()) {
            throw new IllegalArgumentException("Authorization ID cannot be null or blank.");
        }
        validator.validateAmount(amountCents);

        return new PaymentGatewayResponse(
                true,
                authorizationId,
                "CAPTURED",
                null,
                null,
                Map.of("capturedAmountCents", String.valueOf(amountCents))
        );
    }

    @Override
    public PaymentGatewayResponse refundCharge(String transactionId, long amountCents, String reason) {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or blank.");
        }
        validator.validateAmount(amountCents);

        return new PaymentGatewayResponse(
                true,
                "re_" + UUID.randomUUID().toString().substring(0, 10),
                "REFUNDED",
                null,
                null,
                Map.of("refundedCents", String.valueOf(amountCents), "reason", reason != null ? reason : "CUSTOMER_REQUEST")
        );
    }
}