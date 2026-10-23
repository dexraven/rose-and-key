package com.goldgell.roseandkey.repository.booking;

import com.goldgell.roseandkey.domain.booking.PaymentTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class Test_PaymentTransactionRepository_findByGatewayTransactionId extends Test_PaymentTransactionRepository {

    @Test
    @DisplayName("Happy Path: Resolves transaction by unique gateway transaction ID")
    void shouldFindTransactionByGatewayTxId() {
        PaymentTransaction tx = createTransaction("ch_stripe_unique_888", PaymentTransaction.PaymentStatus.AUTHORIZED, 100000L);
        entityManager.persist(tx);
        entityManager.flush();

        Optional<PaymentTransaction> found = paymentTransactionRepository.findByGatewayTransactionId("ch_stripe_unique_888");

        assertThat(found).isPresent();
        assertThat(found.get().getAmountCents()).isEqualTo(100000L);
        assertThat(found.get().getStatus()).isEqualTo(PaymentTransaction.PaymentStatus.AUTHORIZED);
    }

    @Test
    @DisplayName("Negative Scenario: Returns empty Optional when gateway transaction ID does not exist")
    void shouldReturnEmptyWhenGatewayTxIdNotFound() {
        Optional<PaymentTransaction> found = paymentTransactionRepository.findByGatewayTransactionId("ch_non_existent");
        assertThat(found).isEmpty();
    }
}