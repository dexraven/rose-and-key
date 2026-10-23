package com.goldgell.roseandkey.repository.accounting;

import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

@DataJpaTest
public abstract class Test_CommissionLedgerRepository {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected CommissionLedgerRepository commissionLedgerRepository;

    protected BookingRecord bookingRecord;

    @BeforeEach
    void setupBooking() {
        ClientProfile client = entityManager.persistFlushFind(ClientProfile.builder()
                .accountType(ClientProfile.AccountType.REGISTERED)
                .email("comm.client@example.com")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .build());

        TripIntake intake = entityManager.persistFlushFind(TripIntake.builder()
                .client(client)
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .earliestDeparture(LocalDate.now().plusMonths(1))
                .latestReturn(LocalDate.now().plusMonths(1).plusDays(7))
                .partyHeadcount(2)
                .build());

        bookingRecord = entityManager.persistFlushFind(BookingRecord.builder()
                .tripIntake(intake)
                .supplierType(TripIntake.SupplierCategory.DISNEY)
                .supplierConfirmationCode("DIS-COMM-77")
                .packageReferenceId("PKG-001")
                .subtotalCents(550000L)
                .taxesAndFeesCents(50000L)
                .totalCents(600000L)
                .currency("USD")
                .confirmedAt(Instant.now())
                .transactions(new ArrayList<>())
                .build());
    }

    protected CommissionLedgerEntry persistEntry(CommissionLedgerEntry.PayoutStatus status) {
        CommissionLedgerEntry entry = CommissionLedgerEntry.builder()
                .bookingRecord(bookingRecord)
                .grossBookingCents(600000L)
                .supplierCommissionRate(0.10)
                .grossCommissionCents(60000L)
                .hostAgencySplitRate(0.80)
                .netAdvisorPayoutCents(48000L)
                .status(status)
                .expectedTravelDate(LocalDate.now().plusMonths(1))
                .build();
        return entityManager.persistFlushFind(entry);
    }
}