package com.goldgell.roseandkey.domain.booking;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_BookingRecord {

    @Test
    @DisplayName("POJO / Entity: Tests getters, setters, builder defaults, and transaction association")
    void testPojoAndMutations() {
        BookingRecord record = new BookingRecord();
        UUID id = UUID.randomUUID();
        TripIntake intake = new TripIntake();
        Instant now = Instant.now();

        record.setId(id);
        record.setTripIntake(intake);
        record.setSupplierType(TripIntake.SupplierCategory.DISNEY);
        record.setSupplierConfirmationCode("DIS-CONF-88");
        record.setPackageReferenceId("PKG-WDW-1");
        record.setSubtotalCents(450000L);
        record.setTaxesAndFeesCents(50000L);
        record.setTotalCents(500000L);
        record.setCurrency("USD");
        record.setConfirmedAt(now);
        record.setTransactions(new ArrayList<>());
        record.setCreatedAt(now);
        record.setUpdatedAt(now);

        assertThat(record.getId()).isEqualTo(id);
        assertThat(record.getTripIntake()).isEqualTo(intake);
        assertThat(record.getSupplierType()).isEqualTo(TripIntake.SupplierCategory.DISNEY);
        assertThat(record.getSupplierConfirmationCode()).isEqualTo("DIS-CONF-88");
        assertThat(record.getPackageReferenceId()).isEqualTo("PKG-WDW-1");
        assertThat(record.getSubtotalCents()).isEqualTo(450000L);
        assertThat(record.getTaxesAndFeesCents()).isEqualTo(50000L);
        assertThat(record.getTotalCents()).isEqualTo(500000L);
        assertThat(record.getCurrency()).isEqualTo("USD");
        assertThat(record.getConfirmedAt()).isEqualTo(now);

        PaymentTransaction tx = PaymentTransaction.builder()
                .amountCents(500000L)
                .gatewayTransactionId("ch_stripe_123")
                .build();
        record.addTransaction(tx);

        assertThat(record.getTransactions()).contains(tx);
        assertThat(tx.getBookingRecord()).isEqualTo(record);
    }
}