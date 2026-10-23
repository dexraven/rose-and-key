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

public class Test_MarriottSupplierAdapter {

    private MarriottSupplierAdapter adapter;

    @BeforeEach
    void setUp() {
        adapter = new MarriottSupplierAdapter();
    }

    @Test
    @DisplayName("Category Match: Declares MARRIOTT supported category")
    void shouldMatchMarriottCategory() {
        assertThat(adapter.getSupportedCategory()).isEqualTo(SupplierCategory.MARRIOTT);
    }

    @Test
    @DisplayName("Happy Path: Direct booking returns immediate rate calculation")
    void shouldFetchDirectRates() {
        AvailabilityQuery query = AvailabilityQuery.builder()
                .destinationCode("JW_ORLANDO")
                .checkInDate(LocalDate.of(2026, 11, 10))
                .checkOutDate(LocalDate.of(2026, 11, 15))
                .adultCount(2)
                .build();

        List<QuoteRateOption> rates = adapter.fetchRatesAndAvailability(query);

        assertThat(rates).hasSize(1);
        QuoteRateOption rate = rates.getFirst();
        assertThat(rate.isImmediateBookingAvailable()).isTrue();
        assertThat(rate.getTotalCents()).isEqualTo(45000L);
    }

    @Test
    @DisplayName("Happy Path: Locks reservation directly and returns confirmation code")
    void shouldLockMarriottReservation() {
        LockReservationCommand cmd = LockReservationCommand.builder()
                .intakeId("intake-mar-101")
                .authorizedAmountCents(45000L)
                .paymentVaultToken("pm_card_vault")
                .build();

        ReservationResult result = adapter.lockReservation(cmd);

        assertThat(result.isSuccess()).isTrue();
        assertThat(result.getSupplierConfirmationCode()).startsWith("MAR-");
    }
}