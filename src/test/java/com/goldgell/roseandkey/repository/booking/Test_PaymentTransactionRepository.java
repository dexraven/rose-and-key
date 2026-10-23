package com.goldgell.roseandkey.repository.booking;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.domain.booking.PaymentTransaction;
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
public abstract class Test_PaymentTransactionRepository {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected PaymentTransactionRepository paymentTransactionRepository;

    protected BookingRecord persistedBooking;

    @BeforeEach
    void setupBaseEntities() {
        ClientProfile client = ClientProfile.builder()
                .accountType(ClientProfile.AccountType.GUEST)
                .email("test.traveler@example.com")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .build();
        entityManager.persist(client);

        TripIntake intake = TripIntake.builder()
                .client(client)
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .earliestDeparture(LocalDate.now().plusMonths(1))
                .latestReturn(LocalDate.now().plusMonths(1).plusDays(7))
                .partyHeadcount(2)
                .build();
        entityManager.persist(intake);

        persistedBooking = BookingRecord.builder()
                .tripIntake(intake)
                .supplierType(TripIntake.SupplierCategory.DISNEY)
                .supplierConfirmationCode("DIS-CONF-101")
                .packageReferenceId("PKG-001")
                .subtotalCents(450000L)
                .taxesAndFeesCents(50000L)
                .totalCents(500000L)
                .currency("USD")
                .confirmedAt(Instant.now())
                .transactions(new ArrayList<>())
                .build();
        entityManager.persist(persistedBooking);
        entityManager.flush();
    }

    protected PaymentTransaction createTransaction(String txId, PaymentTransaction.PaymentStatus status, long amountCents) {
        return PaymentTransaction.builder()
                .bookingRecord(persistedBooking)
                .gatewayTransactionId(txId)
                .gatewayPaymentMethodToken("pm_card_visa")
                .amountCents(amountCents)
                .currency("USD")
                .status(status)
                .build();
    }
}