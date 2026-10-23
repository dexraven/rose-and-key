package com.goldgell.roseandkey.repository.booking;

import com.goldgell.roseandkey.domain.booking.PaymentTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_PaymentTransactionRepository_findByBookingRecordId extends Test_PaymentTransactionRepository {

    @Test
    @DisplayName("Happy Path: Retrieves all transactions linked to a booking ordered by creation time descending")
    void shouldFindTransactionsByBookingRecordId() {
        PaymentTransaction tx1 = createTransaction("ch_101", PaymentTransaction.PaymentStatus.AUTHORIZED, 250000L);
        PaymentTransaction tx2 = createTransaction("ch_102", PaymentTransaction.PaymentStatus.AUTHORIZED, 250000L);

        entityManager.persist(tx1);
        entityManager.persist(tx2);
        entityManager.flush();

        List<PaymentTransaction> results = paymentTransactionRepository.findByBookingRecordIdOrderByCreatedAtDesc(persistedBooking.getId());

        assertThat(results).hasSize(2);
        assertThat(results).extracting(PaymentTransaction::getGatewayTransactionId).containsExactly("ch_102", "ch_101");
    }

    @Test
    @DisplayName("Edge Case: Returns empty list when booking record has no transactions")
    void shouldReturnEmptyListForUnlinkedBooking() {
        List<PaymentTransaction> results = paymentTransactionRepository.findByBookingRecordIdOrderByCreatedAtDesc(UUID.randomUUID());
        assertThat(results).isEmpty();
    }
}