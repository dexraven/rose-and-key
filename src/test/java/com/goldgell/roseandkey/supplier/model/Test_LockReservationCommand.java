package com.goldgell.roseandkey.supplier.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_LockReservationCommand {

    @Test
    @DisplayName("POJO & Contracts: Verifies getters, setters, builder, equals, and hashCode")
    void testPojoAndContracts() {
        LockReservationCommand cmd1 = LockReservationCommand.builder()
                .packageId("PKG-01")
                .intakeId("intake-77")
                .leadTravelerFirstName("Belle")
                .leadTravelerLastName("French")
                .leadTravelerEmail("belle@castle.org")
                .paymentVaultToken("pm_card_vault_token")
                .authorizedAmountCents(650000L)
                .build();

        LockReservationCommand cmd2 = LockReservationCommand.builder()
                .packageId("PKG-01")
                .intakeId("intake-77")
                .leadTravelerFirstName("Belle")
                .leadTravelerLastName("French")
                .leadTravelerEmail("belle@castle.org")
                .paymentVaultToken("pm_card_vault_token")
                .authorizedAmountCents(650000L)
                .build();

        assertThat(cmd1).isEqualTo(cmd2);
        assertThat(cmd1.hashCode()).isEqualTo(cmd2.hashCode());
        assertThat(cmd1.toString()).contains("intake-77", "pm_card_vault_token");

        LockReservationCommand mutable = new LockReservationCommand();
        mutable.setIntakeId("intake-88");
        mutable.setAuthorizedAmountCents(300000L);

        assertThat(mutable.getIntakeId()).isEqualTo("intake-88");
        assertThat(mutable.getAuthorizedAmountCents()).isEqualTo(300000L);
    }
}