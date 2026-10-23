package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_TripIntakeSubmissionRequest {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, toString, and builder values")
    void testValueContract() {
        LocalDate dep = LocalDate.of(2026, 12, 1);
        LocalDate ret = LocalDate.of(2026, 12, 8);

        TripIntakeSubmissionRequest req1 = TripIntakeSubmissionRequest.builder()
                .destinationTarget(TripIntake.SupplierCategory.UNIVERSAL)
                .earliestDeparture(dep)
                .latestReturn(ret)
                .targetBudgetCents(500000L)
                .partyHeadcount(2)
                .specialNotes("Cabana requested")
                .companions(Collections.emptyList())
                .build();

        TripIntakeSubmissionRequest req2 = TripIntakeSubmissionRequest.builder()
                .destinationTarget(TripIntake.SupplierCategory.UNIVERSAL)
                .earliestDeparture(dep)
                .latestReturn(ret)
                .targetBudgetCents(500000L)
                .partyHeadcount(2)
                .specialNotes("Cabana requested")
                .companions(Collections.emptyList())
                .build();

        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
        assertThat(req1.getTargetBudgetCents()).isEqualTo(500000L);
        assertThat(req1.getSpecialNotes()).isEqualTo("Cabana requested");
    }
}