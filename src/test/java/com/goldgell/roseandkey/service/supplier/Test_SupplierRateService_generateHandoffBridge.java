package com.goldgell.roseandkey.service.supplier;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class Test_SupplierRateService_generateHandoffBridge extends Test_SupplierRateService {

    @Test
    @DisplayName("Happy Path: Prepares agent handoff bridge with deep link and auto-fill payload")
    void shouldGenerateHandoffBridge() {
        SupplierRateQueryRequest request = SupplierRateQueryRequest.builder()
                .category(SupplierCategory.DISNEY)
                .build();
        AvailabilityQuery query = AvailabilityQuery.builder().category(SupplierCategory.DISNEY).build();
        QuoteRateOption handoff = QuoteRateOption.builder().packageId("HANDOFF-1").build();
        SupplierRateResponse response = SupplierRateResponse.builder().packageId("HANDOFF-1").build();

        doNothing().when(supplierCommandValidator).validateRateQuery(request);
        when(supplierRateTranslator.toAvailabilityQuery(request)).thenReturn(query);
        when(supplierGateway.prepareAgentHandoff(SupplierCategory.DISNEY, query, "intake-99")).thenReturn(handoff);
        when(supplierRateTranslator.toResponse(handoff)).thenReturn(response);

        SupplierRateResponse result = supplierRateService.generateHandoffBridge(request, "intake-99");

        assertThat(result.getPackageId()).isEqualTo("HANDOFF-1");
        verify(supplierCommandValidator).validateRateQuery(request);
    }
}