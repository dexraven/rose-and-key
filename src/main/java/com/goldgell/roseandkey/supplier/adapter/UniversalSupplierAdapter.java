package com.goldgell.roseandkey.supplier.adapter;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.gateway.SupplierAdapter;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UniversalSupplierAdapter implements SupplierAdapter {

    private static final String UNIVERSAL_PORTAL_BASE = "https://www.universalpartnercommunity.com/booking";

    @Override
    public SupplierCategory getSupportedCategory() {
        return SupplierCategory.UNIVERSAL;
    }

    @Override
    public List<QuoteRateOption> fetchRatesAndAvailability(AvailabilityQuery query) {
        return List.of(
                QuoteRateOption.builder()
                        .packageId("UO-PKG-" + (query.getDestinationCode() != null ? query.getDestinationCode() : "ORLANDO"))
                        .supplierName("Universal Parks & Resorts Vacations")
                        .displayName("Universal Orlando Resort Hotel + 3-Park Explorer")
                        .totalCents(0L)
                        .currency("USD")
                        .immediateBookingAvailable(false)
                        .deepLinkHandoffUrl(UNIVERSAL_PORTAL_BASE + "?target=" + (query.getDestinationCode() != null ? query.getDestinationCode() : "ORLANDO"))
                        .autoFillPayload(Map.of(
                                "resort_code", query.getDestinationCode() != null ? query.getDestinationCode() : "ORLANDO",
                                "check_in", String.valueOf(query.getCheckInDate()),
                                "check_out", String.valueOf(query.getCheckOutDate()),
                                "adults", String.valueOf(query.getAdultCount())
                        ))
                        .build()
        );
    }

    @Override
    public ReservationResult lockReservation(LockReservationCommand command) {
        return ReservationResult.builder()
                .success(true)
                .bookingReference("UNI-HOLD-" + command.getIntakeId())
                .supplierConfirmationCode("PENDING_PARTNER_SESSION")
                .statusMessage("Universal Partner Community hold request initialized")
                .vendorMetadata(Map.of("intakeId", command.getIntakeId()))
                .build();
    }

    @Override
    public QuoteRateOption generateAutoFillHandoff(AvailabilityQuery query, String intakeId) {
        return QuoteRateOption.builder()
                .packageId("UNIVERSAL-DIRECT-" + intakeId)
                .supplierName("Universal Parks & Resorts")
                .displayName("Universal Partner Auto-Fill Bridge")
                .totalCents(0L)
                .currency("USD")
                .immediateBookingAvailable(false)
                .deepLinkHandoffUrl(UNIVERSAL_PORTAL_BASE + "?intake=" + intakeId)
                .autoFillPayload(Map.of(
                        "intake_id", intakeId,
                        "check_in", String.valueOf(query.getCheckInDate()),
                        "check_out", String.valueOf(query.getCheckOutDate())
                ))
                .build();
    }
}