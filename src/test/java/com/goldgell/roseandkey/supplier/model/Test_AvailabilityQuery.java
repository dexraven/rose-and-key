package com.goldgell.roseandkey.supplier.model;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_AvailabilityQuery {

    @Test
    @DisplayName("POJO & Contracts: Verifies getters, setters, builder, equals, and hashCode")
    void testPojoAndContracts() {
        LocalDate checkIn = LocalDate.of(2026, 12, 19);
        LocalDate checkOut = LocalDate.of(2026, 12, 27);

        AvailabilityQuery query1 = AvailabilityQuery.builder()
                .category(SupplierCategory.DISNEY)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .adultCount(2)
                .childAges(List.of(7, 10))
                .destinationCode("WDW")
                .preferredResortTier("DELUXE")
                .build();

        AvailabilityQuery query2 = AvailabilityQuery.builder()
                .category(SupplierCategory.DISNEY)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .adultCount(2)
                .childAges(List.of(7, 10))
                .destinationCode("WDW")
                .preferredResortTier("DELUXE")
                .build();

        AvailabilityQuery diff = AvailabilityQuery.builder()
                .category(SupplierCategory.UNIVERSAL)
                .build();

        assertThat(query1).isEqualTo(query2);
        assertThat(query1.hashCode()).isEqualTo(query2.hashCode());
        assertThat(query1).isNotEqualTo(diff);
        assertThat(query1.toString()).contains("WDW", "DELUXE");

        AvailabilityQuery mutable = new AvailabilityQuery();
        mutable.setCategory(SupplierCategory.MARRIOTT);
        mutable.setAdultCount(4);
        mutable.setDestinationCode("JW_MARRIOTT");

        assertThat(mutable.getCategory()).isEqualTo(SupplierCategory.MARRIOTT);
        assertThat(mutable.getAdultCount()).isEqualTo(4);
        assertThat(mutable.getDestinationCode()).isEqualTo("JW_MARRIOTT");
    }
}