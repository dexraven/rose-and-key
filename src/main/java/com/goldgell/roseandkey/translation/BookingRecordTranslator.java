package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.api.response.BookingConfirmationResponse;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class BookingRecordTranslator {

    public BookingRecord toEntity(LockReservationRequest request, TripIntake intake, ReservationResult reservation) {
        return BookingRecord.builder()
                .tripIntake(intake)
                .supplierType(request.getCategory())
                .supplierConfirmationCode(reservation.getSupplierConfirmationCode())
                .packageReferenceId(request.getPackageId())
                .subtotalCents(request.getSubtotalCents())
                .taxesAndFeesCents(request.getTaxesAndFeesCents())
                .totalCents(request.getTotalCents())
                .currency("USD")
                .confirmedAt(Instant.now())
                .build();
    }

    public BookingConfirmationResponse toResponse(BookingRecord record, String statusMessage) {
        return BookingConfirmationResponse.builder()
                .bookingRecordId(record.getId())
                .tripIntakeId(record.getTripIntake().getId())
                .supplierType(record.getSupplierType())
                .supplierConfirmationCode(record.getSupplierConfirmationCode())
                .packageReferenceId(record.getPackageReferenceId())
                .totalCents(record.getTotalCents())
                .currency(record.getCurrency())
                .confirmedAt(record.getConfirmedAt())
                .statusMessage(statusMessage)
                .build();
    }
}