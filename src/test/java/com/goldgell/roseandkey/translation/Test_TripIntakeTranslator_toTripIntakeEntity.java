package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Test_TripIntakeTranslator_toTripIntakeEntity extends Test_TripIntakeTranslator {

    @Test
    @DisplayName("Happy Path: Maps submission request and associations to TripIntake entity")
    void shouldMapToTripIntakeEntity() {
        ClientProfile client = ClientProfile.builder().build();
        LocalDate dep = LocalDate.now().plusMonths(1);
        LocalDate ret = dep.plusDays(7);

        TripIntakeSubmissionRequest req = TripIntakeSubmissionRequest.builder()
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .earliestDeparture(dep)
                .latestReturn(ret)
                .targetBudgetCents(1000000L)
                .partyHeadcount(3)
                .specialNotes("High floor")
                .build();

        TripIntake intake = translator.toTripIntakeEntity(req, client, "agent-kelley-01");

        assertThat(intake.getClient()).isEqualTo(client);
        assertThat(intake.getAssignedAgentId()).isEqualTo("agent-kelley-01");
        assertThat(intake.getDestinationTarget()).isEqualTo(TripIntake.SupplierCategory.DISNEY);
        assertThat(intake.getStatus()).isEqualTo(TripIntake.TripStatus.INTAKE_SUBMITTED);
        assertThat(intake.getEarliestDeparture()).isEqualTo(dep);
        assertThat(intake.getLatestReturn()).isEqualTo(ret);
        assertThat(intake.getTargetBudgetCents()).isEqualTo(1000000L);
        assertThat(intake.getPartyHeadcount()).isEqualTo(3);
        assertThat(intake.getSpecialNotes()).isEqualTo("High floor");
    }
}