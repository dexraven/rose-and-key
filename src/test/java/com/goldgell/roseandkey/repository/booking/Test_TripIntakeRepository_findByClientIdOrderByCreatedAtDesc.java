package com.goldgell.roseandkey.repository.intake;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_TripIntakeRepository_findByClientIdOrderByCreatedAtDesc extends Test_TripIntakeRepository {

    @Test
    @DisplayName("Happy Path: Retrieves all intakes for a given client ordered by creation time descending")
    void shouldFindIntakesByClientId() {
        TripIntake intake1 = persistIntake("agent-kelley-01", LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(1).plusDays(5));
        TripIntake intake2 = persistIntake("agent-kelley-01", LocalDate.now().plusMonths(3), LocalDate.now().plusMonths(3).plusDays(7));

        List<TripIntake> results = tripIntakeRepository.findByClientIdOrderByCreatedAtDesc(client.getId());

        assertThat(results).hasSize(2);
        assertThat(results).extracting(TripIntake::getId).containsExactly(intake2.getId(), intake1.getId());
    }

    @Test
    @DisplayName("Edge Case: Returns empty list for client with no intakes")
    void shouldReturnEmptyForNewClient() {
        List<TripIntake> results = tripIntakeRepository.findByClientIdOrderByCreatedAtDesc(UUID.randomUUID());
        assertThat(results).isEmpty();
    }
}