package com.goldgell.roseandkey.repository.booking;

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
public abstract class Test_BookingRecordRepository {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected BookingRecordRepository bookingRecordRepository;

    protected TripIntake persistedIntake;

    @BeforeEach
    void setupIntake() {
        ClientProfile client = entityManager.persistFlushFind(ClientProfile.builder()
                .accountType(ClientProfile.AccountType.GUEST)
                .email("lead@travel.com")
                .firstName("Adam")
                .lastName("Beast")
                .phoneE164("+12105550188")
                .build());

        persistedIntake = entityManager.persistFlushFind(TripIntake.builder()
                .client(client)
                .destinationTarget(TripIntake.SupplierCategory.MARRIOTT)
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .earliestDeparture(LocalDate.now().plusMonths(2))
                .latestReturn(LocalDate.now().plusMonths(2).plusDays(5))
                .partyHeadcount(2)
                .build());
    }

    protected BookingRecord persistBookingRecord(String confirmationCode, long totalCents) {
        BookingRecord record = BookingRecord.builder()
                .tripIntake(persistedIntake)
                .supplierType(TripIntake.SupplierCategory.MARRIOTT)
                .supplierConfirmationCode(confirmationCode)
                .packageReferenceId("PKG-MAR-LUX")
                .subtotalCents(totalCents - 10000L)
                .taxesAndFeesCents(10000L)
                .totalCents(totalCents)
                .currency("USD")
                .confirmedAt(Instant.now())
                .transactions(new ArrayList<>())
                .build();
        return entityManager.persistFlushFind(record);
    }
}