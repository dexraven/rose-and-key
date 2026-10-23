package com.goldgell.roseandkey.supplier.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_QuoteRateOption {

    @Test
    @DisplayName("POJO & Contracts: Verifies getters, setters, builder, equals, and hashCode")
    void testPojoAndContracts() {
        QuoteRateOption opt1 = QuoteRateOption.builder()
                .packageId("PKG-WDW-01")
                .supplierName("Disney Destinations")
                .displayName("Grand Floridian Lagoon View")
                .totalCents(850000L)
                .currency("USD")
                .immediateBookingAvailable(false)
                .deepLinkHandoffUrl("https://disneytravelagents.com/123")
                .autoFillPayload(Map.of("resort", "GF"))
                .build();

        QuoteRateOption opt2 = QuoteRateOption.builder()
                .packageId("PKG-WDW-01")
                .supplierName("Disney Destinations")
                .displayName("Grand Floridian Lagoon View")
                .totalCents(850000L)
                .currency("USD")
                .immediateBookingAvailable(false)
                .deepLinkHandoffUrl("https://disneytravelagents.com/123")
                .autoFillPayload(Map.of("resort", "GF"))
                .build();

        assertThat(opt1).isEqualTo(opt2);
        assertThat(opt1.hashCode()).isEqualTo(opt2.hashCode());
        assertThat(opt1.toString()).contains("PKG-WDW-01", "850000");

        QuoteRateOption mutable = new QuoteRateOption();
        mutable.setPackageId("PKG-UNI-02");
        mutable.setTotalCents(450000L);
        mutable.setImmediateBookingAvailable(true);

        assertThat(mutable.getPackageId()).isEqualTo("PKG-UNI-02");
        assertThat(mutable.getTotalCents()).isEqualTo(450000L);
        assertThat(mutable.isImmediateBookingAvailable()).isTrue();
    }
}