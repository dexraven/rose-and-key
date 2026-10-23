package com.goldgell.roseandkey.service.booking;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.booking.PaymentTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class Test_PaymentTransactionService_recordAuthorizedTransaction extends Test_PaymentTransactionService {

    @Test
    @DisplayName("Happy Path: Validates, links to booking, and records AUTHORIZED transaction")
    void shouldRecordAuthorizedTransaction() {
        UUID bookingId = UUID.randomUUID();
        BookingRecord booking = createBookingRecord(bookingId);

        doNothing().when(paymentTransactionValidator).validatePaymentAuthorization("pm_valid_tok", 50000L, "USD");
        when(bookingRecordRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(paymentTransactionRepository.save(any(PaymentTransaction.class))).thenAnswer(i -> i.getArgument(0));

        PaymentTransaction tx = paymentTransactionService.recordAuthorizedTransaction(
                bookingId, "pm_valid_tok", 50000L, "USD", "ch_stripe_123");

        assertThat(tx).isNotNull();
        assertThat(tx.getStatus()).isEqualTo(PaymentTransaction.PaymentStatus.AUTHORIZED);
        assertThat(tx.getAmountCents()).isEqualTo(50000L);
        assertThat(tx.getCurrency()).isEqualTo("USD");
        assertThat(tx.getGatewayTransactionId()).isEqualTo("ch_stripe_123");
        assertThat(booking.getTransactions()).contains(tx);
        verify(paymentTransactionRepository).save(any(PaymentTransaction.class));
    }

    @Test
    @DisplayName("Negative Scenario: Throws when booking record does not exist")
    void shouldThrowWhenBookingNotFound() {
        UUID missingId = UUID.randomUUID();
        when(bookingRecordRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentTransactionService.recordAuthorizedTransaction(
                missingId, "pm_tok", 1000L, "USD", "ch_123"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Booking record not found");

        verifyNoInteractions(paymentTransactionRepository);
    }
}