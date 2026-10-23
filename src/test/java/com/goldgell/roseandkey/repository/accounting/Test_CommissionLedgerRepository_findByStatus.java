package com.goldgell.roseandkey.repository.accounting;

import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Test_CommissionLedgerRepository_findByStatus extends Test_CommissionLedgerRepository {

    @Test
    @DisplayName("Happy Path: Filters commission entries by payout status")
    void shouldFilterEntriesByStatus() {
        persistEntry(CommissionLedgerEntry.PayoutStatus.PENDING_TRAVEL_COMPLETION);

        List<CommissionLedgerEntry> pending = commissionLedgerRepository
                .findByStatus(CommissionLedgerEntry.PayoutStatus.PENDING_TRAVEL_COMPLETION);
        List<CommissionLedgerEntry> disbursed = commissionLedgerRepository
                .findByStatus(CommissionLedgerEntry.PayoutStatus.DISBURSED_TO_ADVISOR);

        assertThat(pending).hasSize(1);
        assertThat(disbursed).isEmpty();
    }
}