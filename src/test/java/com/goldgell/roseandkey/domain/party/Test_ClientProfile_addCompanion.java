package com.goldgell.roseandkey.domain.party;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Test_ClientProfile_addCompanion extends Test_ClientProfile {

    @Test
    @DisplayName("Happy Path: Adds companion and wires bidirectional relationship")
    void shouldAddCompanionAndWireBackReference() {
        ClientProfile profile = createDefaultProfile(ClientProfile.AccountType.GUEST);
        Companion companion = Companion.builder()
                .firstName("Maurice")
                .lastName("French")
                .relationship(Companion.Relationship.PARENT)
                .dateOfBirth(LocalDate.of(1965, 3, 10))
                .build();

        profile.addCompanion(companion);

        assertThat(profile.getCompanions()).hasSize(1);
        assertThat(profile.getCompanions().getFirst()).isEqualTo(companion);
        assertThat(companion.getPrimaryClient()).isEqualTo(profile);
    }

    @Test
    @DisplayName("Negative Scenario: Adding null companion throws exception")
    void shouldThrowWhenAddingNullCompanion() {
        ClientProfile profile = createDefaultProfile(ClientProfile.AccountType.GUEST);

        assertThatThrownBy(() -> profile.addCompanion(null))
                .isInstanceOf(Exception.class);
    }
}