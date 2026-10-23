package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.BookingConfirmationResponse;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_BookingRecordTranslator_toResponse extends Test_BookingRecordTranslator {

    @Test
    @DisplayName("Happy Path: Maps BookingRecord entity to outbound confirmation response DTO")
    void shouldMapEntityToConfirmationResponse() {
        UUID bookingId = UUID.randomUUID();
        UUID intakeId = UUID.randomUUID();
        Instant confirmedAt = Instant.now();

        TripIntake intake = TripIntake.builder().id(intakeId).build();
        BookingRecord record = BookingRecord.builder()
                .id(bookingId)
                .tripIntake(intake)
                .supplierType(SupplierCategory.DISNEY)
                .supplierConfirmationCode("DSN-999")
                .packageReferenceId("WDW-PKG")
                .totalCents(750000L)
                .currency("USD")
                .confirmedAt(confirmedAt)
                .build();

        BookingConfirmationResponse response = translator.toResponse(record, "Hold locked successfully");

        assertThat(response.getBookingRecordId()).isEqualTo(bookingId);
        assertThat(response.getTripIntakeId()).isEqualTo(intakeId);
        assertThat(response.getSupplierConfirmationCode()).isEqualTo("DSN-999");
        assertThat(response.getStatusMessage()).isEqualTo("Hold locked successfully");
    }
}