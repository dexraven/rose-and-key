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

class Test_SupplierGateway_queryRates extends Test_SupplierGateway {

    @Test
    @DisplayName("Happy Path: Routes query to the registered category adapter")
    void shouldRouteToRegisteredAdapter() {
        when(disneyAdapter.getSupportedCategory()).thenReturn(SupplierCategory.DISNEY);
        when(marriottAdapter.getSupportedCategory()).thenReturn(SupplierCategory.MARRIOTT);
        supplierGateway = new SupplierGateway(List.of(disneyAdapter, marriottAdapter));

        AvailabilityQuery query = createQuery(SupplierCategory.DISNEY);
        QuoteRateOption option = QuoteRateOption.builder().packageId("WDW-1").build();
        when(disneyAdapter.fetchRatesAndAvailability(query)).thenReturn(List.of(option));

        List<QuoteRateOption> rates = supplierGateway.queryRates(query);

        assertThat(rates).hasSize(1);
        assertThat(rates.getFirst().getPackageId()).isEqualTo("WDW-1");
    }

    @Test
    @DisplayName("Negative Scenario: Throws UnsupportedOperationException for unregistered category")
    void shouldThrowForUnregisteredCategory() {
        when(disneyAdapter.getSupportedCategory()).thenReturn(SupplierCategory.DISNEY);
        supplierGateway = new SupplierGateway(List.of(disneyAdapter));

        AvailabilityQuery universalQuery = createQuery(SupplierCategory.UNIVERSAL);

        assertThatThrownBy(() -> supplierGateway.queryRates(universalQuery))
                .isInstanceOf(UnsupportedOperationException.class)
                .hasMessageContaining("No supplier adapter registered for category: UNIVERSAL");
    }

    @Test
    @DisplayName("Negative Scenario: Null query or null category throws IllegalArgumentException")
    void shouldThrowOnNullInput() {
        assertThatThrownBy(() -> supplierGateway.queryRates(null))
                .isInstanceOf(IllegalArgumentException.class);
    }
}