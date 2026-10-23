package com.goldgell.roseandkey.service.booking;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.booking.PaymentTransaction;
import com.goldgell.roseandkey.repository.booking.BookingRecordRepository;
import com.goldgell.roseandkey.repository.booking.PaymentTransactionRepository;
import com.goldgell.roseandkey.validation.PaymentTransactionValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private static final Logger log = LoggerFactory.getLogger(PaymentTransactionService.class);

    private final BookingRecordRepository bookingRecordRepository;
    private final PaymentTransactionRepository paymentTransactionRepository;
    private final PaymentTransactionValidator paymentTransactionValidator;

    @Transactional
    public PaymentTransaction recordAuthorizedTransaction(
            UUID bookingRecordId,
            String paymentMethodToken,
            long amountCents,
            String currency,
            String gatewayTxId) {

        paymentTransactionValidator.validatePaymentAuthorization(paymentMethodToken, amountCents, currency);

        BookingRecord booking = bookingRecordRepository.findById(bookingRecordId)
                .orElseThrow(() -> new IllegalArgumentException("Booking record not found: " + bookingRecordId));

        PaymentTransaction transaction = PaymentTransaction.builder()
                .bookingRecord(booking)
                .gatewayPaymentMethodToken(paymentMethodToken)
                .amountCents(amountCents)
                .currency(currency.toUpperCase())
                .gatewayTransactionId(gatewayTxId)
                .status(PaymentTransaction.PaymentStatus.AUTHORIZED)
                .build();

        booking.addTransaction(transaction);
        PaymentTransaction saved = paymentTransactionRepository.save(transaction);
        log.info("Recorded Authorized Payment [{}] for Booking [{}]", saved.getId(), bookingRecordId);
        return saved;
    }

    @Transactional
    public PaymentTransaction recordFailedTransaction(
            UUID bookingRecordId,
            String paymentMethodToken,
            long amountCents,
            String currency,
            String errorMessage) {

        paymentTransactionValidator.validatePaymentMethodToken(paymentMethodToken);

        BookingRecord booking = bookingRecordRepository.findById(bookingRecordId)
                .orElseThrow(() -> new IllegalArgumentException("Booking record not found: " + bookingRecordId));

        PaymentTransaction transaction = PaymentTransaction.builder()
                .bookingRecord(booking)
                .gatewayPaymentMethodToken(paymentMethodToken)
                .amountCents(amountCents)
                .currency(currency != null ? currency.toUpperCase() : "USD")
                .status(PaymentTransaction.PaymentStatus.FAILED)
                .errorMessage(errorMessage)
                .build();

        booking.addTransaction(transaction);
        PaymentTransaction saved = paymentTransactionRepository.save(transaction);
        log.warn("Recorded Failed Payment [{}] for Booking [{}]: {}", saved.getId(), bookingRecordId, errorMessage);
        return saved;
    }
}