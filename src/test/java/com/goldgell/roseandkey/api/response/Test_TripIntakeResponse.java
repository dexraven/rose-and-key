package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_TripIntakeResponse {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, and value getters")
    void testValueContract() {
        UUID intakeId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();
        LocalDate now = LocalDate.now();

        TripIntakeResponse res1 = TripIntakeResponse.builder()
                .intakeId(intakeId)
                .clientId(clientId)
                .accountType(ClientProfile.AccountType.REGISTERED)
                .leadTravelerName("Belle French")
                .leadTravelerEmail("belle@french.com")
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .status(TripIntake.TripStatus.CONFIRMED)
                .earliestDeparture(now)
                .latestReturn(now.plusDays(5))
                .targetBudgetCents(120000L)
                .partyHeadcount(2)
                .build();

        TripIntakeResponse res2 = TripIntakeResponse.builder()
                .intakeId(intakeId)
                .clientId(clientId)
                .accountType(ClientProfile.AccountType.REGISTERED)
                .leadTravelerName("Belle French")
                .leadTravelerEmail("belle@french.com")
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .status(TripIntake.TripStatus.CONFIRMED)
                .earliestDeparture(now)
                .latestReturn(now.plusDays(5))
                .targetBudgetCents(120000L)
                .partyHeadcount(2)
                .build();

        assertThat(res1).isEqualTo(res2);
        assertThat(res1.hashCode()).isEqualTo(res2.hashCode());
        assertThat(res1.getIntakeId()).isEqualTo(intakeId);
        assertThat(res1.getLeadTravelerName()).isEqualTo("Belle French");
    }
}