package com.goldgell.roseandkey.domain.party;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class Test_Companion_isMinorOnDate extends Test_Companion {

    @Test
    @DisplayName("Happy Path: Flags minor correctly before 18th birthday")
    void shouldIdentifyMinor() {
        Companion companion = createDefaultCompanion(); // DOB: 2014-06-15
        assertThat(companion.isMinorOnDate(LocalDate.of(2026, 6, 15))).isTrue();
    }

    @Test
    @DisplayName("Boundary: False on exactly 18th birthday")
    void shouldReturnFalseOn18thBirthday() {
        Companion companion = createDefaultCompanion(); // DOB: 2014-06-15
        assertThat(companion.isMinorOnDate(LocalDate.of(2032, 6, 15))).isFalse();
    }
}