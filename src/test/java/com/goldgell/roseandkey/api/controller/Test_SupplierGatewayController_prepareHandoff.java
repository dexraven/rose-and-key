package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_SupplierGatewayController_prepareHandoff extends Test_SupplierGatewayController {

    @Test
    @DisplayName("Happy Path: POST /api/v1/suppliers/handoff/{intakeId} returns 200 OK with prefilled auto-fill package")
    void shouldReturn200WithHandoffPackage() throws Exception {
        LocalDate checkIn = LocalDate.now().plusMonths(1);
        SupplierRateQueryRequest request = SupplierRateQueryRequest.builder()
                .category(SupplierCategory.UNIVERSAL)
                .checkInDate(checkIn)
                .checkOutDate(checkIn.plusDays(4))
                .adultCount(2)
                .destinationCode("ORLANDO")
                .build();

        SupplierRateResponse mockResponse = SupplierRateResponse.builder()
                .packageId("UNIVERSAL-DIRECT-intake-99")
                .supplierName("Universal Parks & Resorts")
                .displayName("Universal Partner Auto-Fill Bridge")
                .deepLinkHandoffUrl("https://universalpartnercommunity.com?intake=intake-99")
                .autoFillPayload(Map.of("intake_id", "intake-99"))
                .build();

        when(supplierRateService.generateHandoffBridge(any(SupplierRateQueryRequest.class), eq("intake-99")))
                .thenReturn(mockResponse);

        mockMvc.perform(post("/api/v1/suppliers/handoff/{intakeId}", "intake-99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.packageId").value("UNIVERSAL-DIRECT-intake-99"))
                .andExpect(jsonPath("$.autoFillPayload.intake_id").value("intake-99"));
    }

    @Test
    @DisplayName("Negative Scenario: POST /api/v1/suppliers/handoff/{intakeId} returns 400 Bad Request on business validation failure")
    void shouldReturn400OnInvalidDateBounds() throws Exception {
        LocalDate checkIn = LocalDate.now().plusMonths(1);
        SupplierRateQueryRequest request = SupplierRateQueryRequest.builder()
                .category(SupplierCategory.UNIVERSAL)
                .checkInDate(checkIn)
                .checkOutDate(checkIn.minusDays(1)) // Invalid bounds
                .adultCount(2)
                .build();

        when(supplierRateService.generateHandoffBridge(any(SupplierRateQueryRequest.class), eq("intake-99")))
                .thenThrow(new IllegalArgumentException("Check-out date cannot precede check-in date."));

        mockMvc.perform(post("/api/v1/suppliers/handoff/{intakeId}", "intake-99")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Check-out date cannot precede check-in date."));
    }
}