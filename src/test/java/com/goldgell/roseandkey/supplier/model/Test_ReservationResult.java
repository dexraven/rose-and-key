package com.goldgell.roseandkey.supplier.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_ReservationResult {

    @Test
    @DisplayName("POJO & Contracts: Verifies getters, setters, builder, equals, and hashCode")
    void testPojoAndContracts() {
        ReservationResult res1 = ReservationResult.builder()
                .success(true)
                .supplierConfirmationCode("CONF-MAR-99")
                .bookingReference("HOLD-123")
                .statusMessage("Confirmed hold on stateroom")
                .vendorMetadata(Map.of("tier", "luxury"))
                .build();

        ReservationResult res2 = ReservationResult.builder()
                .success(true)
                .supplierConfirmationCode("CONF-MAR-99")
                .bookingReference("HOLD-123")
                .statusMessage("Confirmed hold on stateroom")
                .vendorMetadata(Map.of("tier", "luxury"))
                .build();

        assertThat(res1).isEqualTo(res2);
        assertThat(res1.hashCode()).isEqualTo(res2.hashCode());
        assertThat(res1.isSuccess()).isTrue();
        assertThat(res1.getSupplierConfirmationCode()).isEqualTo("CONF-MAR-99");
        assertThat(res1.toString()).contains("CONF-MAR-99", "HOLD-123");

        ReservationResult mutable = new ReservationResult();
        mutable.setSuccess(false);
        mutable.setStatusMessage("Session expired");

        assertThat(mutable.isSuccess()).isFalse();
        assertThat(mutable.getStatusMessage()).isEqualTo("Session expired");
    }
}