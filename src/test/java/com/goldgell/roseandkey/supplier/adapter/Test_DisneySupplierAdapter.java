package com.goldgell.roseandkey.supplier.adapter;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_DisneySupplierAdapter {

    private DisneySupplierAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new DisneySupplierAdapter();
    }

    @Test
    @DisplayName("Category Match: Declares DISNEY supported category")
    void shouldMatchDisneyCategory() {
        assertThat(adapter.getSupportedCategory()).isEqualTo(SupplierCategory.DISNEY);
    }

    @Test
    @DisplayName("Happy Path: Builds rates, deep-link URL, and auto-fill payload with children")
    void shouldGenerateRatesAndPayload() {
        AvailabilityQuery query = AvailabilityQuery.builder()
                .destinationCode("WDW")
                .checkInDate(LocalDate.of(2026, 12, 19))
                .checkOutDate(LocalDate.of(2026, 12, 27))
                .adultCount(2)
                .childAges(List.of(7, 10))
                .build();

        List<QuoteRateOption> rates = adapter.fetchRatesAndAvailability(query);

        assertThat(rates).hasSize(1);
        QuoteRateOption rate = rates.getFirst();
        assertThat(rate.getDeepLinkHandoffUrl()).contains("destination=WDW", "checkIn=2026-12-19", "children=2");
        assertThat(rate.getAutoFillPayload()).containsEntry("field_adult_count", "2");
        assertThat(rate.getAutoFillPayload()).containsEntry("field_child_count", "2");
    }

    @Test
    @DisplayName("Happy Path: Locks reservation returning pending agent session reference")
    void shouldLockReservation() {
        LockReservationCommand cmd = LockReservationCommand.builder()
                .intakeId("intake-wdw-99")
                .leadTravelerEmail("belle@castle.org")
                .build();

        ReservationResult result = adapter.lockReservation(cmd);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getBookingReference()).contains("intake-wdw-99");
        assertThat(result.getSupplierConfirmationCode()).isEqualTo("PENDING_AGENT_SESSION");
    }
}