package com.goldgell.roseandkey.service.supplier;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class Test_SupplierRateService_getRates extends Test_SupplierRateService {

    @Test
    @DisplayName("Happy Path: Validates query, calls gateway, and maps rates to responses")
    void shouldQueryRatesSuccessfully() {
        SupplierRateQueryRequest request = SupplierRateQueryRequest.builder().build();
        AvailabilityQuery query = AvailabilityQuery.builder().build();
        QuoteRateOption option = QuoteRateOption.builder().packageId("PKG-1").build();
        SupplierRateResponse response = SupplierRateResponse.builder().packageId("PKG-1").build();

        doNothing().when(supplierCommandValidator).validateRateQuery(request);
        when(supplierRateTranslator.toAvailabilityQuery(request)).thenReturn(query);
        when(supplierGateway.queryRates(query)).thenReturn(List.of(option));
        when(supplierRateTranslator.toResponse(option)).thenReturn(response);

        List<SupplierRateResponse> results = supplierRateService.getRates(request);

        assertThat(results).hasSize(1);
        assertThat(results.getFirst().getPackageId()).isEqualTo("PKG-1");
        verify(supplierCommandValidator).validateRateQuery(request);
    }
}