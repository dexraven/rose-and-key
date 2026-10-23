package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Test_SupplierCommandValidator {

    protected SupplierCommandValidator validator;

    @BeforeEach
    void setUp() {
        validator = new SupplierCommandValidator();
    }

    protected SupplierRateQueryRequest createValidRateQuery() {
        LocalDate checkIn = LocalDate.now().plusMonths(1);
        return SupplierRateQueryRequest.builder()
                .category(SupplierCategory.DISNEY)
                .checkInDate(checkIn)
                .checkOutDate(checkIn.plusDays(5))
                .adultCount(2)
                .destinationCode("WDW")
                .build();
    }

    protected LockReservationRequest createValidLockRequest(long subtotal, long taxes, long total) {
        return LockReservationRequest.builder()
                .tripIntakeId(UUID.randomUUID())
                .category(SupplierCategory.DISNEY)
                .packageId("PKG-WDW-1")
                .subtotalCents(subtotal)
                .taxesAndFeesCents(taxes)
                .totalCents(total)
                .build();
    }
}