package com.goldgell.roseandkey.repository.accounting;

import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CommissionLedgerRepository extends JpaRepository<CommissionLedgerEntry, UUID> {
    Optional<CommissionLedgerEntry> findByBookingRecordId(UUID bookingRecordId);
    List<CommissionLedgerEntry> findByStatus(CommissionLedgerEntry.PayoutStatus status);
}