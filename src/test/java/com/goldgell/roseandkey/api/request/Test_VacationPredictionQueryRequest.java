package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_VacationPredictionQueryRequest {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, toString, and builder values")
    void testValueContract() {
        VacationPredictionQueryRequest req1 = VacationPredictionQueryRequest.builder()
                .destination(SupplierCategory.DISNEY)
                .targetYear(2027)
                .build();

        VacationPredictionQueryRequest req2 = VacationPredictionQueryRequest.builder()
                .destination(SupplierCategory.DISNEY)
                .targetYear(2027)
                .build();

        VacationPredictionQueryRequest diff = VacationPredictionQueryRequest.builder()
                .destination(SupplierCategory.UNIVERSAL)
                .targetYear(2028)
                .build();

        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
        assertThat(req1).isNotEqualTo(diff);
        assertThat(req1.toString()).contains("DISNEY", "2027");
        assertThat(req1.getDestination()).isEqualTo(SupplierCategory.DISNEY);
        assertThat(req1.getTargetYear()).isEqualTo(2027);
    }
}