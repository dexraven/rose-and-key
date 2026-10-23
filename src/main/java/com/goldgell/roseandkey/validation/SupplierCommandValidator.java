package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class SupplierCommandValidator {

    public void validateRateQuery(SupplierRateQueryRequest query) {
        if (query.getCheckOutDate().isBefore(query.getCheckInDate())) {
            throw new IllegalArgumentException("Check-out date cannot precede check-in date.");
        }
        if (query.getCheckInDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check-in date cannot be in the past.");
        }
    }

    public void validateLockRequest(LockReservationRequest request) {
        if (request.getTotalCents() != (request.getSubtotalCents() + request.getTaxesAndFeesCents())) {
            throw new IllegalArgumentException("Total cents must equal subtotal plus taxes and fees.");
        }
    }
}