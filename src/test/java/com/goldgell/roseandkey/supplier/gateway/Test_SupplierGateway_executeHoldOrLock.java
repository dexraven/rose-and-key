package com.goldgell.roseandkey.supplier.gateway;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class Test_SupplierGateway_executeHoldOrLock extends Test_SupplierGateway {

    @Test
    @DisplayName("Happy Path: Routes reservation execution to target adapter")
    void shouldRouteLockToAdapter() {
        when(marriottAdapter.getSupportedCategory()).thenReturn(SupplierCategory.MARRIOTT);
        supplierGateway = new SupplierGateway(List.of(marriottAdapter));

        LockReservationCommand cmd = createCommand();
        ReservationResult expected = ReservationResult.builder().success(true).build();
        when(marriottAdapter.lockReservation(cmd)).thenReturn(expected);

        ReservationResult result = supplierGateway.executeHoldOrLock(SupplierCategory.MARRIOTT, cmd);

        assertThat(result.isSuccess()).isTrue();
    }

    @Test
    @DisplayName("Negative Scenario: Null command throws IllegalArgumentException")
    void shouldThrowOnNullCommand() {
        assertThatThrownBy(() -> supplierGateway.executeHoldOrLock(SupplierCategory.MARRIOTT, null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}