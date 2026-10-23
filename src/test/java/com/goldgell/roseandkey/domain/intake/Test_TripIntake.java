package com.goldgell.roseandkey.domain.intake;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_TripIntake {

    @Test
    @DisplayName("POJO / Entity: Tests getters, setters, builder defaults, and mutation boundaries")
    void testPojoAndMutations() {
        TripIntake intake = new TripIntake();
        UUID id = UUID.randomUUID();
        ClientProfile client = new ClientProfile();
        LocalDate departure = LocalDate.of(2026, 12, 19);
        LocalDate returnDate = LocalDate.of(2026, 12, 27);
        Instant now = Instant.now();

        intake.setId(id);
        intake.setClient(client);
        intake.setAssignedAgentId("agent-kelley-01");
        intake.setDestinationTarget(TripIntake.SupplierCategory.DISNEY);
        intake.setStatus(TripIntake.TripStatus.INTAKE_SUBMITTED);
        intake.setEarliestDeparture(departure);
        intake.setLatestReturn(returnDate);
        intake.setTargetBudgetCents(1500000L);
        intake.setPreferredCurrency("USD");
        intake.setPartyHeadcount(4);
        intake.setSpecialNotes("Castle view requested");
        intake.setCreatedAt(now);
        intake.setUpdatedAt(now);

        assertThat(intake.getId()).isEqualTo(id);
        assertThat(intake.getClient()).isEqualTo(client);
        assertThat(intake.getAssignedAgentId()).isEqualTo("agent-kelley-01");
        assertThat(intake.getDestinationTarget()).isEqualTo(TripIntake.SupplierCategory.DISNEY);
        assertThat(intake.getStatus()).isEqualTo(TripIntake.TripStatus.INTAKE_SUBMITTED);
        assertThat(intake.getEarliestDeparture()).isEqualTo(departure);
        assertThat(intake.getLatestReturn()).isEqualTo(returnDate);
        assertThat(intake.getTargetBudgetCents()).isEqualTo(1500000L);
        assertThat(intake.getPreferredCurrency()).isEqualTo("USD");
        assertThat(intake.getPartyHeadcount()).isEqualTo(4);
        assertThat(intake.getSpecialNotes()).isEqualTo("Castle view requested");
        assertThat(intake.getCreatedAt()).isEqualTo(now);
        assertThat(intake.getUpdatedAt()).isEqualTo(now);

        // Builder currency default check
        TripIntake built = TripIntake.builder().build();
        assertThat(built.getPreferredCurrency()).isEqualTo("USD");
    }
}