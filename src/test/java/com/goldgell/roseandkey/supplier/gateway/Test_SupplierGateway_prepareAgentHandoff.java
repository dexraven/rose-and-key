package com.goldgell.roseandkey.supplier.gateway;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class Test_SupplierGateway_prepareAgentHandoff extends Test_SupplierGateway {

    @Test
    @DisplayName("Happy Path: Generates handoff package with auto-fill payload")
    void shouldGenerateHandoff() {
        when(disneyAdapter.getSupportedCategory()).thenReturn(SupplierCategory.DISNEY);
        supplierGateway = new SupplierGateway(List.of(disneyAdapter));

        AvailabilityQuery query = createQuery(SupplierCategory.DISNEY);
        QuoteRateOption handoff = QuoteRateOption.builder().packageId("HANDOFF-1").build();
        when(disneyAdapter.generateAutoFillHandoff(query, "intake-123")).thenReturn(handoff);

        QuoteRateOption result = supplierGateway.prepareAgentHandoff(SupplierCategory.DISNEY, query, "intake-123");

        assertThat(result.getPackageId()).isEqualTo("HANDOFF-1");
    }

    @Test
    @DisplayName("Negative Scenario: Blank intake ID throws IllegalArgumentException")
    void shouldThrowOnBlankIntakeId() {
        AvailabilityQuery query = createQuery(SupplierCategory.DISNEY);

        assertThatThrownBy(() -> supplierGateway.prepareAgentHandoff(SupplierCategory.DISNEY, query, "   "))
                .isInstanceOf(IllegalArgumentException.class);
    }
}