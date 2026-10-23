package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.CompanionResponse;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Test_ClientProfileTranslator_toCompanionResponse extends Test_ClientProfileTranslator {

    @Test
    @DisplayName("Happy Path: Maps companion properties and evaluates minor flag based on current date")
    void shouldMapCompanionAndFlagMinor() {
        Companion minorComp = createCompanion("Chip", LocalDate.now().minusYears(12));
        CompanionResponse response = translator.toCompanionResponse(minorComp);

        assertThat(response.getFirstName()).isEqualTo("Chip");
        assertThat(response.isMinor()).isTrue();

        Companion adultComp = createCompanion("Maurice", LocalDate.now().minusYears(50));
        assertThat(translator.toCompanionResponse(adultComp).isMinor()).isFalse();
    }
}