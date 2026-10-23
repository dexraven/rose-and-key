package com.goldgell.roseandkey.domain.accounting;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_CommissionLedgerEntry {

    @Test
    @DisplayName("POJO / Entity: Tests getters, setters, all-args, no-args, and status updates")
    void testPojoAndMutations() {
        CommissionLedgerEntry entry = new CommissionLedgerEntry();
        UUID id = UUID.randomUUID();
        BookingRecord record = new BookingRecord();
        LocalDate expectedTravel = LocalDate.of(2026, 12, 19);
        LocalDate disbursed = LocalDate.of(2027, 1, 5);

        entry.setId(id);
        entry.setBookingRecord(record);
        entry.setGrossBookingCents(1000000L);
        entry.setSupplierCommissionRate(0.10);
        entry.setGrossCommissionCents(100000L);
        entry.setHostAgencySplitRate(0.80);
        entry.setNetAdvisorPayoutCents(80000L);
        entry.setStatus(CommissionLedgerEntry.PayoutStatus.PENDING_TRAVEL_COMPLETION);
        entry.setExpectedTravelDate(expectedTravel);
        entry.setActualDisbursementDate(disbursed);

        assertThat(entry.getId()).isEqualTo(id);
        assertThat(entry.getBookingRecord()).isEqualTo(record);
        assertThat(entry.getGrossBookingCents()).isEqualTo(1000000L);
        assertThat(entry.getSupplierCommissionRate()).isEqualTo(0.10);
        assertThat(entry.getGrossCommissionCents()).isEqualTo(100000L);
        assertThat(entry.getHostAgencySplitRate()).isEqualTo(0.80);
        assertThat(entry.getNetAdvisorPayoutCents()).isEqualTo(80000L);
        assertThat(entry.getStatus()).isEqualTo(CommissionLedgerEntry.PayoutStatus.PENDING_TRAVEL_COMPLETION);
        assertThat(entry.getExpectedTravelDate()).isEqualTo(expectedTravel);
        assertThat(entry.getActualDisbursementDate()).isEqualTo(disbursed);

        CommissionLedgerEntry built = CommissionLedgerEntry.builder()
                .status(CommissionLedgerEntry.PayoutStatus.DISBURSED_TO_ADVISOR)
                .build();
        assertThat(built.getStatus()).isEqualTo(CommissionLedgerEntry.PayoutStatus.DISBURSED_TO_ADVISOR);
    }
}