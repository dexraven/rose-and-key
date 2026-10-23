package com.goldgell.roseandkey.supplier.gateway;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import com.goldgell.roseandkey.supplier.model.ReservationResult;

import java.util.List;

public interface SupplierAdapter {
    SupplierCategory getSupportedCategory();
    List<QuoteRateOption> fetchRatesAndAvailability(AvailabilityQuery query);
    ReservationResult lockReservation(LockReservationCommand command);
    QuoteRateOption generateAutoFillHandoff(AvailabilityQuery query, String intakeId);
}