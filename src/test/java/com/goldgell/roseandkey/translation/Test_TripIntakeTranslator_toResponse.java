package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.TripIntakeResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_TripIntakeTranslator_toResponse extends Test_TripIntakeTranslator {

    @Test
    @DisplayName("Happy Path: Maps persisted TripIntake entity to Outbound Response DTO")
    void shouldMapEntityToResponseDto() {
        UUID intakeId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        ClientProfile client = ClientProfile.builder()
                .id(clientId)
                .accountType(ClientProfile.AccountType.GUEST)
                .firstName("Belle")
                .lastName("French")
                .email("belle@villagelibrary.org")
                .build();

        TripIntake intake = TripIntake.builder()
                .id(intakeId)
                .client(client)
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .earliestDeparture(LocalDate.of(2026, 12, 19))
                .latestReturn(LocalDate.of(2026, 12, 27))
                .targetBudgetCents(850000L)
                .partyHeadcount(2)
                .build();

        TripIntakeResponse res = translator.toResponse(intake);

        assertThat(res.getIntakeId()).isEqualTo(intakeId);
        assertThat(res.getClientId()).isEqualTo(clientId);
        assertThat(res.getAccountType()).isEqualTo(ClientProfile.AccountType.GUEST);
        assertThat(res.getLeadTravelerName()).isEqualTo("Belle French");
        assertThat(res.getLeadTravelerEmail()).isEqualTo("belle@villagelibrary.org");
        assertThat(res.getDestinationTarget()).isEqualTo(TripIntake.SupplierCategory.DISNEY);
        assertThat(res.getStatus()).isEqualTo(TripIntake.TripStatus.INTAKE_SUBMITTED);
        assertThat(res.getTargetBudgetCents()).isEqualTo(850000L);
    }
}