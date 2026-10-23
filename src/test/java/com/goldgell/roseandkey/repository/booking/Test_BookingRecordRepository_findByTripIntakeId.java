package com.goldgell.roseandkey.repository.booking;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_BookingRecordRepository_findByTripIntakeId extends Test_BookingRecordRepository {

    @Test
    @DisplayName("Happy Path: Resolves booking records associated with a trip intake ID")
    void shouldFindBookingsByTripIntakeId() {
        BookingRecord record = persistBookingRecord("MAR-CONF-101", 350000L);

        List<BookingRecord> results = bookingRecordRepository.findByTripIntakeId(persistedIntake.getId());

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getId()).isEqualTo(record.getId());
        assertThat(results.getFirst().getSupplierConfirmationCode()).isEqualTo("MAR-CONF-101");
    }

    @Test
    @DisplayName("Edge Case: Returns empty list when trip intake has no associated booking records")
    void shouldReturnEmptyWhenNoBookingsExist() {
        List<BookingRecord> results = bookingRecordRepository.findByTripIntakeId(UUID.randomUUID());
        assertThat(results).isEmpty();
    }
}