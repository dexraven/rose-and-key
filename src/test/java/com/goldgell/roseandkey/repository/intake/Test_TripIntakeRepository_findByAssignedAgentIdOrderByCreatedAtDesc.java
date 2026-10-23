package com.goldgell.roseandkey.repository.intake;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Test_TripIntakeRepository_findByAssignedAgentIdOrderByCreatedAtDesc extends Test_TripIntakeRepository {

    @Test
    @DisplayName("Happy Path: Retrieves all trip intakes for assigned agent ordered by creation time descending")
    void shouldFindIntakesByAgentId() {
        TripIntake intake1 = persistIntake("agent-kelley-01", LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(1).plusDays(7));
        TripIntake intake2 = persistIntake("agent-kelley-01", LocalDate.now().plusMonths(2), LocalDate.now().plusMonths(2).plusDays(7));
        persistIntake("agent-other-02", LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(3).plusDays(7));

        List<TripIntake> results = tripIntakeRepository.findByAssignedAgentIdOrderByCreatedAtDesc("agent-kelley-01");

        assertThat(results).hasSize(2);
        assertThat(results).extracting(TripIntake::getId).containsExactly(intake2.getId(), intake1.getId());
    }

    @Test
    @DisplayName("Edge Case: Returns empty list when agent has no assigned intakes")
    void shouldReturnEmptyForUnassignedAgent() {
        List<TripIntake> results = tripIntakeRepository.findByAssignedAgentIdOrderByCreatedAtDesc("agent-none");
        assertThat(results).isEmpty();
    }
}