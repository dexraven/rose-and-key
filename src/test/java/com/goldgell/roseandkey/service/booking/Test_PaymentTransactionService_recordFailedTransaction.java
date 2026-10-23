package com.goldgell.roseandkey.service.booking;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.booking.PaymentTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class Test_PaymentTransactionService_recordFailedTransaction extends Test_PaymentTransactionService {

    @Test
    @DisplayName("Happy Path: Records FAILED transaction with error diagnostic message")
    void shouldRecordFailedTransaction() {
        UUID bookingId = UUID.randomUUID();
        BookingRecord booking = createBookingRecord(bookingId);

        doNothing().when(paymentTransactionValidator).validatePaymentMethodToken("pm_bad_tok");
        when(bookingRecordRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(paymentTransactionRepository.save(any(PaymentTransaction.class))).thenAnswer(i -> i.getArgument(0));

        PaymentTransaction tx = paymentTransactionService.recordFailedTransaction(
                bookingId, "pm_bad_tok", 25000L, "USD", "Card declined: Insufficient funds");

        assertThat(tx).isNotNull();
        assertThat(tx.getStatus()).isEqualTo(PaymentTransaction.PaymentStatus.FAILED);
        assertThat(tx.getErrorMessage()).isEqualTo("Card declined: Insufficient funds");
        assertThat(booking.getTransactions()).contains(tx);
        verify(paymentTransactionRepository).save(any(PaymentTransaction.class));
    }
}