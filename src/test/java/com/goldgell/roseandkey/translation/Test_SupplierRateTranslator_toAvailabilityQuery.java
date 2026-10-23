package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Test_SupplierRateTranslator_toAvailabilityQuery extends Test_SupplierRateTranslator {

    @Test
    @DisplayName("Happy Path: Translates API request DTO to supplier domain query")
    void shouldTranslateRequestToDomainQuery() {
        LocalDate checkIn = LocalDate.of(2026, 12, 19);
        LocalDate checkOut = LocalDate.of(2026, 12, 27);
        SupplierRateQueryRequest request = SupplierRateQueryRequest.builder()
                .category(SupplierCategory.DISNEY)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .adultCount(2)
                .childAges(List.of(7, 10))
                .destinationCode("WDW")
                .preferredResortTier("DELUXE")
                .build();

        AvailabilityQuery query = translator.toAvailabilityQuery(request);

        assertThat(query.getCategory()).isEqualTo(SupplierCategory.DISNEY);
        assertThat(query.getCheckInDate()).isEqualTo(checkIn);
        assertThat(query.getCheckOutDate()).isEqualTo(checkOut);
        assertThat(query.getAdultCount()).isEqualTo(2);
        assertThat(query.getChildAges()).containsExactly(7, 10);
        assertThat(query.getDestinationCode()).isEqualTo("WDW");
        assertThat(query.getPreferredResortTier()).isEqualTo("DELUXE");
    }
}