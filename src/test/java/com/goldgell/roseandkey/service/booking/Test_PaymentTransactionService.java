package com.goldgell.roseandkey.service.booking;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.repository.booking.BookingRecordRepository;
import com.goldgell.roseandkey.repository.booking.PaymentTransactionRepository;
import com.goldgell.roseandkey.validation.PaymentTransactionValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public abstract class Test_PaymentTransactionService {

    @Mock
    protected BookingRecordRepository bookingRecordRepository;

    @Mock
    protected PaymentTransactionRepository paymentTransactionRepository;

    @Mock
    protected PaymentTransactionValidator paymentTransactionValidator;

    @InjectMocks
    protected PaymentTransactionService paymentTransactionService;

    protected BookingRecord createBookingRecord(UUID id) {
        return BookingRecord.builder()
                .id(id)
                .transactions(new ArrayList<>())
                .build();
    }
}