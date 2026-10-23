package com.goldgell.roseandkey.service.booking;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.api.response.BookingConfirmationResponse;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.repository.booking.BookingRecordRepository;
import com.goldgell.roseandkey.repository.intake.TripIntakeRepository;
import com.goldgell.roseandkey.supplier.gateway.SupplierGateway;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import com.goldgell.roseandkey.translation.BookingRecordTranslator;
import com.goldgell.roseandkey.validation.SupplierCommandValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookingRecordService {

    private static final Logger log = LoggerFactory.getLogger(BookingRecordService.class);

    private final TripIntakeRepository tripIntakeRepository;
    private final BookingRecordRepository bookingRecordRepository;
    private final SupplierGateway supplierGateway;
    private final SupplierCommandValidator supplierCommandValidator;
    private final BookingRecordTranslator bookingRecordTranslator;

    @Transactional
    public BookingConfirmationResponse lockAndConfirmBooking(LockReservationRequest request) {
        supplierCommandValidator.validateLockRequest(request);

        TripIntake intake = tripIntakeRepository.findById(request.getTripIntakeId())
                .orElseThrow(() -> new IllegalArgumentException("Trip intake not found: " + request.getTripIntakeId()));

        LockReservationCommand command = LockReservationCommand.builder()
                .packageId(request.getPackageId())
                .intakeId(intake.getId().toString())
                .leadTravelerFirstName(intake.getClient().getFirstName())
                .leadTravelerLastName(intake.getClient().getLastName())
                .leadTravelerEmail(intake.getClient().getEmail())
                .paymentVaultToken(request.getPaymentMethodToken() != null ? request.getPaymentMethodToken() : intake.getClient().getDefaultPaymentMethodToken())
                .authorizedAmountCents(request.getTotalCents())
                .build();

        ReservationResult vendorResult = supplierGateway.executeHoldOrLock(request.getCategory(), command);

        if (!vendorResult.isSuccess()) {
            throw new IllegalStateException("Supplier hold failed: " + vendorResult.getStatusMessage());
        }

        intake.setStatus(TripIntake.TripStatus.BOOKING_IN_PROGRESS);
        tripIntakeRepository.save(intake);

        BookingRecord record = bookingRecordTranslator.toEntity(request, intake, vendorResult);
        BookingRecord savedRecord = bookingRecordRepository.save(record);

        log.info("Persisted Booking Record [{}] for Intake [{}]", savedRecord.getId(), intake.getId());
        return bookingRecordTranslator.toResponse(savedRecord, vendorResult.getStatusMessage());
    }
}