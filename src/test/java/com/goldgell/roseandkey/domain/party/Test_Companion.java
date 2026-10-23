package com.goldgell.roseandkey.domain.party;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_Companion {

    protected Companion createDefaultCompanion() {
        return Companion.builder()
                .id(UUID.randomUUID())
                .firstName("Chip")
                .lastName("Potts")
                .relationship(Companion.Relationship.CHILD)
                .dateOfBirth(LocalDate.of(2014, 6, 15))
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    @DisplayName("POJO / Entity: Verifies getters, setters, all-args, no-args, and mutation")
    void testPojoAndMutations() {
        Companion companion = new Companion();
        UUID id = UUID.randomUUID();
        ClientProfile client = new ClientProfile();
        LocalDate dob = LocalDate.of(1995, 3, 20);
        Instant now = Instant.now();

        companion.setId(id);
        companion.setPrimaryClient(client);
        companion.setFirstName("Lumiere");
        companion.setLastName("Candle");
        companion.setRelationship(Companion.Relationship.COMPANION);
        companion.setDateOfBirth(dob);
        companion.setCreatedAt(now);
        companion.setUpdatedAt(now);

        assertThat(companion.getId()).isEqualTo(id);
        assertThat(companion.getPrimaryClient()).isEqualTo(client);
        assertThat(companion.getFirstName()).isEqualTo("Lumiere");
        assertThat(companion.getLastName()).isEqualTo("Candle");
        assertThat(companion.getRelationship()).isEqualTo(Companion.Relationship.COMPANION);
        assertThat(companion.getDateOfBirth()).isEqualTo(dob);
        assertThat(companion.getCreatedAt()).isEqualTo(now);
        assertThat(companion.getUpdatedAt()).isEqualTo(now);
    }
}