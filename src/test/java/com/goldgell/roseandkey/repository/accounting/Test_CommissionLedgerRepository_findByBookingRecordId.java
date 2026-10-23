package com.goldgell.roseandkey.repository.accounting;

import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_CommissionLedgerRepository_findByBookingRecordId extends Test_CommissionLedgerRepository {

    @Test
    @DisplayName("Happy Path: Resolves commission ledger entry by associated booking record ID")
    void shouldFindEntryByBookingRecordId() {
        CommissionLedgerEntry entry = persistEntry(CommissionLedgerEntry.PayoutStatus.PENDING_TRAVEL_COMPLETION);

        Optional<CommissionLedgerEntry> found = commissionLedgerRepository.findByBookingRecordId(bookingRecord.getId());

        assertThat(found).isPresent();
        assertThat(found.get().getId()).isEqualTo(entry.getId());
        assertThat(found.get().getNetAdvisorPayoutCents()).isEqualTo(48000L);
    }

    @Test
    @DisplayName("Negative Scenario: Returns empty Optional when booking record has no ledger entry")
    void shouldReturnEmptyWhenNotFound() {
        Optional<CommissionLedgerEntry> found = commissionLedgerRepository.findByBookingRecordId(UUID.randomUUID());
        assertThat(found).isEmpty();
    }
}