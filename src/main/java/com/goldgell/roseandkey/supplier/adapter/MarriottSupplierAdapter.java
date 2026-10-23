package com.goldgell.roseandkey.supplier.adapter;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.gateway.SupplierAdapter;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class MarriottSupplierAdapter implements SupplierAdapter {

    @Override
    public SupplierCategory getSupportedCategory() {
        return SupplierCategory.MARRIOTT;
    }

    @Override
    public List<QuoteRateOption> fetchRatesAndAvailability(AvailabilityQuery query) {
        // Direct GDS/CRS live estimate simulation for Marriott Luxury/Resort portfolio
        return List.of(
                QuoteRateOption.builder()
                        .packageId("MAR-RESORT-" + (query.getDestinationCode() != null ? query.getDestinationCode() : "DIRECT"))
                        .supplierName("Marriott International / Bonvoy")
                        .displayName("Marriott Luxury Resort Stay - Travel Advisor Exclusive")
                        .totalCents(45000L) // Rate in cents ($450.00 / night baseline)
                        .currency("USD")
                        .immediateBookingAvailable(true)
                        .deepLinkHandoffUrl("https://www.marriott.com/travel-agents.mi")
                        .autoFillPayload(Map.of("iata_agency", "GOLDGELL_TRAVEL"))
                        .build()
        );
    }

    @Override
    public ReservationResult lockReservation(LockReservationCommand command) {
        String confCode = "MAR-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return ReservationResult.builder()
                .success(true)
                .bookingReference("RES-" + command.getIntakeId())
                .supplierConfirmationCode(confCode)
                .statusMessage("Marriott direct room reservation locked and hold confirmed")
                .vendorMetadata(Map.of(
                        "confirmationCode", confCode,
                        "amountCents", command.getAuthorizedAmountCents(),
                        "vaultTokenUsed", command.getPaymentVaultToken() != null
                ))
                .build();
    }

    @Override
    public QuoteRateOption generateAutoFillHandoff(AvailabilityQuery query, String intakeId) {
        return QuoteRateOption.builder()
                .packageId("MARRIOTT-DIRECT-" + intakeId)
                .supplierName("Marriott International")
                .displayName("Marriott Travel Agent Direct Link")
                .totalCents(0L)
                .currency("USD")
                .immediateBookingAvailable(true)
                .deepLinkHandoffUrl("https://www.marriott.com/travel-agents.mi?intake=" + intakeId)
                .autoFillPayload(Map.of("intake_id", intakeId))
                .build();
    }
}