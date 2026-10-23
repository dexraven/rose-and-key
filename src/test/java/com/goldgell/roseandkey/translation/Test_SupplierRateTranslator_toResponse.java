package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class Test_SupplierRateTranslator_toResponse extends Test_SupplierRateTranslator {

    @Test
    @DisplayName("Happy Path: Converts supplier quote model to API response")
    void shouldConvertQuoteOptionToResponse() {
        QuoteRateOption option = QuoteRateOption.builder()
                .packageId("PKG-001")
                .supplierName("Disney")
                .displayName("WDW Resort")
                .totalCents(500000L)
                .currency("USD")
                .immediateBookingAvailable(true)
                .deepLinkHandoffUrl("https://disneytravelagents.com/123")
                .autoFillPayload(Map.of("field", "value"))
                .build();

        SupplierRateResponse response = translator.toResponse(option);

        assertThat(response.getPackageId()).isEqualTo("PKG-001");
        assertThat(response.getTotalCents()).isEqualTo(500000L);
        assertThat(response.isImmediateBookingAvailable()).isTrue();
        assertThat(response.getAutoFillPayload()).containsEntry("field", "value");
    }
}