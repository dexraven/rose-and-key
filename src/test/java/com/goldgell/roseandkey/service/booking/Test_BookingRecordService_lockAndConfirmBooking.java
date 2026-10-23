package com.goldgell.roseandkey.service.booking;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.api.response.BookingConfirmationResponse;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class Test_BookingRecordService_lockAndConfirmBooking extends Test_BookingRecordService {

    @Test
    @DisplayName("Happy Path: Validates, executes supplier hold, updates intake status, and saves booking")
    void shouldLockAndConfirmSuccessfully() {
        UUID intakeId = UUID.randomUUID();
        TripIntake intake = createMockIntake(intakeId);

        LockReservationRequest request = LockReservationRequest.builder()
                .tripIntakeId(intakeId)
                .category(SupplierCategory.DISNEY)
                .packageId("PKG-001")
                .totalCents(500000L)
                .paymentMethodToken(null) // relies on client's default token
                .build();

        ReservationResult vendorResult = ReservationResult.builder()
                .success(true)
                .supplierConfirmationCode("DSN-CONF-1")
                .statusMessage("Hold confirmed")
                .build();

        BookingRecord record = BookingRecord.builder().id(UUID.randomUUID()).build();
        BookingConfirmationResponse expectedResponse = BookingConfirmationResponse.builder().build();

        doNothing().when(supplierCommandValidator).validateLockRequest(request);
        when(tripIntakeRepository.findById(intakeId)).thenReturn(Optional.of(intake));
        when(supplierGateway.executeHoldOrLock(eq(SupplierCategory.DISNEY), any(LockReservationCommand.class)))
                .thenReturn(vendorResult);
        when(bookingRecordTranslator.toEntity(request, intake, vendorResult)).thenReturn(record);
        when(bookingRecordRepository.save(record)).thenReturn(record);
        when(bookingRecordTranslator.toResponse(record, "Hold confirmed")).thenReturn(expectedResponse);

        BookingConfirmationResponse result = bookingRecordService.lockAndConfirmBooking(request);

        assertThat(result).isNotNull();
        assertThat(intake.getStatus()).isEqualTo(TripIntake.TripStatus.BOOKING_IN_PROGRESS);
        verify(tripIntakeRepository).save(intake);
        verify(bookingRecordRepository).save(record);
    }

    @Test
    @DisplayName("Negative Scenario: Throws IllegalStateException when vendor hold fails")
    void shouldThrowWhenSupplierHoldFails() {
        UUID intakeId = UUID.randomUUID();
        TripIntake intake = createMockIntake(intakeId);

        LockReservationRequest request = LockReservationRequest.builder()
                .tripIntakeId(intakeId)
                .category(SupplierCategory.DISNEY)
                .build();

        ReservationResult failureResult = ReservationResult.builder()
                .success(false)
                .statusMessage("Supplier session expired")
                .build();

        when(tripIntakeRepository.findById(intakeId)).thenReturn(Optional.of(intake));
        when(supplierGateway.executeHoldOrLock(any(), any())).thenReturn(failureResult);

        assertThatThrownBy(() -> bookingRecordService.lockAndConfirmBooking(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Supplier hold failed: Supplier session expired");

        verifyNoInteractions(bookingRecordRepository);
    }
}