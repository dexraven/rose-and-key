package com.goldgell.roseandkey.supplier.adapter;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.gateway.SupplierAdapter;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
public class DisneySupplierAdapter implements SupplierAdapter {

    private static final String DISNEY_PORTAL_BASE = "https://www.disneytravelagents.com/booking/vacation-packages";

    @Override
    public SupplierCategory getSupportedCategory() {
        return SupplierCategory.DISNEY;
    }

    @Override
    public List<QuoteRateOption> fetchRatesAndAvailability(AvailabilityQuery query) {
        return List.of(
                QuoteRateOption.builder()
                        .packageId("WDW-RESORT-" + (query.getDestinationCode() != null ? query.getDestinationCode() : "WDW"))
                        .supplierName("Disney Destinations")
                        .displayName("Walt Disney World Resort Hotel & Ticket Package")
                        .totalCents(0L)
                        .currency("USD")
                        .immediateBookingAvailable(false)
                        .deepLinkHandoffUrl(buildDisneyDeepLink(query))
                        .autoFillPayload(buildAutoFillPayload(query))
                        .build()
        );
    }

    @Override
    public ReservationResult lockReservation(LockReservationCommand command) {
        return ReservationResult.builder()
                .success(true)
                .bookingReference("DSN-HOLD-" + command.getIntakeId())
                .supplierConfirmationCode("PENDING_AGENT_SESSION")
                .statusMessage("Handoff manifest generated for Disney Travel Agents agent session")
                .vendorMetadata(Map.of("intakeId", command.getIntakeId(), "leadEmail", command.getLeadTravelerEmail()))
                .build();
    }

    @Override
    public QuoteRateOption generateAutoFillHandoff(AvailabilityQuery query, String intakeId) {
        return QuoteRateOption.builder()
                .packageId("DISNEY-DIRECT-" + intakeId)
                .supplierName("Disney Destinations")
                .displayName("Disney Travel Agents Quick-Entry Bridge")
                .totalCents(0L)
                .currency("USD")
                .immediateBookingAvailable(false)
                .deepLinkHandoffUrl(buildDisneyDeepLink(query))
                .autoFillPayload(buildAutoFillPayload(query))
                .build();
    }

    private String buildDisneyDeepLink(AvailabilityQuery query) {
        String dest = query.getDestinationCode() != null ? query.getDestinationCode() : "WDW";
        return String.format(
                "%s?destination=%s&checkIn=%s&checkOut=%s&adults=%d&children=%d",
                DISNEY_PORTAL_BASE,
                URLEncoder.encode(dest, StandardCharsets.UTF_8),
                query.getCheckInDate(),
                query.getCheckOutDate(),
                query.getAdultCount(),
                query.getChildAges() != null ? query.getChildAges().size() : 0
        );
    }

    private Map<String, String> buildAutoFillPayload(AvailabilityQuery query) {
        Map<String, String> payload = new LinkedHashMap<>();
        payload.put("field_destination", query.getDestinationCode() != null ? query.getDestinationCode() : "WDW");
        payload.put("field_checkin", String.valueOf(query.getCheckInDate()));
        payload.put("field_checkout", String.valueOf(query.getCheckOutDate()));
        payload.put("field_adult_count", String.valueOf(query.getAdultCount()));
        payload.put("field_child_count", String.valueOf(query.getChildAges() != null ? query.getChildAges().size() : 0));
        return Collections.unmodifiableMap(payload);
    }
}