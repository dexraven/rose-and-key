package com.goldgell.roseandkey.domain.booking;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_PaymentTransaction {

    @Test
    @DisplayName("POJO / Entity: Tests getters, setters, builder defaults, and status mutation")
    void testPojoAndMutations() {
        PaymentTransaction tx = new PaymentTransaction();
        UUID id = UUID.randomUUID();
        BookingRecord record = new BookingRecord();
        Instant now = Instant.now();

        tx.setId(id);
        tx.setBookingRecord(record);
        tx.setGatewayTransactionId("ch_stripe_999");
        tx.setGatewayPaymentMethodToken("pm_token_777");
        tx.setAmountCents(250000L);
        tx.setCurrency("USD");
        tx.setStatus(PaymentTransaction.PaymentStatus.AUTHORIZED);
        tx.setErrorMessage(null);
        tx.setCreatedAt(now);

        assertThat(tx.getId()).isEqualTo(id);
        assertThat(tx.getBookingRecord()).isEqualTo(record);
        assertThat(tx.getGatewayTransactionId()).isEqualTo("ch_stripe_999");
        assertThat(tx.getGatewayPaymentMethodToken()).isEqualTo("pm_token_777");
        assertThat(tx.getAmountCents()).isEqualTo(250000L);
        assertThat(tx.getCurrency()).isEqualTo("USD");
        assertThat(tx.getStatus()).isEqualTo(PaymentTransaction.PaymentStatus.AUTHORIZED);
        assertThat(tx.getErrorMessage()).isNull();
        assertThat(tx.getCreatedAt()).isEqualTo(now);

        PaymentTransaction failed = PaymentTransaction.builder()
                .status(PaymentTransaction.PaymentStatus.FAILED)
                .errorMessage("Processor timeout")
                .build();

        assertThat(failed.getStatus()).isEqualTo(PaymentTransaction.PaymentStatus.FAILED);
        assertThat(failed.getErrorMessage()).isEqualTo("Processor timeout");
        assertThat(failed.getCurrency()).isEqualTo("USD"); // default
    }
}