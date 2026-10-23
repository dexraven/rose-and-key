package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.CompanionInputRequest;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Test_TripIntakeTranslator_toCompanionEntity extends Test_TripIntakeTranslator {

    @Test
    @DisplayName("Happy Path: Correctly translates companion DTO to entity")
    void shouldMapCompanionDtoToEntity() {
        LocalDate dob = LocalDate.of(1985, 4, 1);
        CompanionInputRequest req = CompanionInputRequest.builder()
                .firstName("  Lumiere  ")
                .lastName("  Maître  ")
                .relationship(Companion.Relationship.COMPANION)
                .dateOfBirth(dob)
                .build();

        Companion companion = translator.toCompanionEntity(req);

        assertThat(companion.getFirstName()).isEqualTo("Lumiere");
        assertThat(companion.getLastName()).isEqualTo("Maître");
        assertThat(companion.getRelationship()).isEqualTo(Companion.Relationship.COMPANION);
        assertThat(companion.getDateOfBirth()).isEqualTo(dob);
    }
}