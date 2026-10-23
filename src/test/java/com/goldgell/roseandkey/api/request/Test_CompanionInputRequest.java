package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_CompanionInputRequest {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, toString, and builder values")
    void testValueContract() {
        LocalDate dob = LocalDate.of(1991, 11, 22);
        CompanionInputRequest comp1 = CompanionInputRequest.builder()
                .firstName("Lumiere")
                .lastName("Maître")
                .relationship(Companion.Relationship.COMPANION)
                .dateOfBirth(dob)
                .build();

        CompanionInputRequest comp2 = CompanionInputRequest.builder()
                .firstName("Lumiere")
                .lastName("Maître")
                .relationship(Companion.Relationship.COMPANION)
                .dateOfBirth(dob)
                .build();

        assertThat(comp1).isEqualTo(comp2);
        assertThat(comp1.hashCode()).isEqualTo(comp2.hashCode());
        assertThat(comp1.getFirstName()).isEqualTo("Lumiere");
        assertThat(comp1.getDateOfBirth()).isEqualTo(dob);
    }
}