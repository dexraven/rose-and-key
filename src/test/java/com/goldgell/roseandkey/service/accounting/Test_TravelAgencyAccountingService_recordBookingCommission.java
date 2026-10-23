package com.goldgell.roseandkey.service.accounting;

import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class Test_TravelAgencyAccountingService_recordBookingCommission extends Test_TravelAgencyAccountingService {

    @Test
    @DisplayName("Happy Path: Accrues gross commission and computes advisor net split")
    void shouldRecordCommissionAccurately() {
        TripIntake intake = TripIntake.builder().earliestDeparture(LocalDate.of(2026, 12, 19)).build();
        BookingRecord record = BookingRecord.builder()
                .id(UUID.randomUUID())
                .tripIntake(intake)
                .totalCents(800000L) // $8,000 package
                .build();

        when(commissionLedgerRepository.save(any(CommissionLedgerEntry.class))).thenAnswer(i -> i.getArgument(0));

        // 10% Disney commission, 80% host split
        CommissionLedgerEntry entry = accountingService.recordBookingCommission(record, 0.10, 0.80);

        assertThat(entry.getGrossBookingCents()).isEqualTo(800000L);
        assertThat(entry.getGrossCommissionCents()).isEqualTo(80000L); // $800.00
        assertThat(entry.getNetAdvisorPayoutCents()).isEqualTo(64000L); // $640.00 (80%)
        assertThat(entry.getStatus()).isEqualTo(CommissionLedgerEntry.PayoutStatus.PENDING_TRAVEL_COMPLETION);
    }
}