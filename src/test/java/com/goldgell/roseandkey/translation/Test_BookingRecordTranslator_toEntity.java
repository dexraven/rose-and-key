package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_BookingRecordTranslator_toEntity extends Test_BookingRecordTranslator {

    @Test
    @DisplayName("Happy Path: Maps reservation request, intake, and supplier result into persisted BookingRecord entity")
    void shouldMapToBookingRecordEntity() {
        TripIntake intake = TripIntake.builder().id(UUID.randomUUID()).build();
        LockReservationRequest request = LockReservationRequest.builder()
                .category(SupplierCategory.MARRIOTT)
                .packageId("PKG-MAR-10")
                .subtotalCents(90000L)
                .taxesAndFeesCents(10000L)
                .totalCents(100000L)
                .build();

        ReservationResult vendorResult = ReservationResult.builder()
                .supplierConfirmationCode("MAR-CONF-99")
                .build();

        BookingRecord record = translator.toEntity(request, intake, vendorResult);

        assertThat(record.getTripIntake()).isEqualTo(intake);
        assertThat(record.getSupplierType()).isEqualTo(SupplierCategory.MARRIOTT);
        assertThat(record.getSupplierConfirmationCode()).isEqualTo("MAR-CONF-99");
        assertThat(record.getPackageReferenceId()).isEqualTo("PKG-MAR-10");
        assertThat(record.getSubtotalCents()).isEqualTo(90000L);
        assertThat(record.getTotalCents()).isEqualTo(100000L);
        assertThat(record.getCurrency()).isEqualTo("USD");
        assertThat(record.getConfirmedAt()).isNotNull();
    }
}