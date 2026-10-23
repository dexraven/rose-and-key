package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_SupplierGatewayController_queryRates extends Test_SupplierGatewayController {

    @Test
    @DisplayName("Happy Path: POST /api/v1/suppliers/rates returns 200 OK with list of rate quotes")
    void shouldReturn200WithRateQuotes() throws Exception {
        LocalDate checkIn = LocalDate.now().plusMonths(2);
        LocalDate checkOut = checkIn.plusDays(7);

        SupplierRateQueryRequest request = SupplierRateQueryRequest.builder()
                .category(SupplierCategory.DISNEY)
                .checkInDate(checkIn)
                .checkOutDate(checkOut)
                .adultCount(2)
                .childAges(List.of(8, 12))
                .destinationCode("WDW")
                .preferredResortTier("DELUXE")
                .build();

        SupplierRateResponse mockRate = SupplierRateResponse.builder()
                .packageId("WDW-RESORT-DELUXE")
                .supplierName("Disney Destinations")
                .displayName("Disney's Grand Floridian Resort & Spa")
                .totalCents(850000L)
                .currency("USD")
                .immediateBookingAvailable(false)
                .deepLinkHandoffUrl("https://disneytravelagents.com/quote-1")
                .autoFillPayload(Map.of("resort", "GF"))
                .build();

        when(supplierRateService.getRates(any(SupplierRateQueryRequest.class))).thenReturn(List.of(mockRate));

        mockMvc.perform(post("/api/v1/suppliers/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].packageId").value("WDW-RESORT-DELUXE"))
                .andExpect(jsonPath("$[0].displayName").value("Disney's Grand Floridian Resort & Spa"))
                .andExpect(jsonPath("$[0].totalCents").value(850000L));
    }

    @Test
    @DisplayName("Negative Scenario: POST /api/v1/suppliers/rates returns 400 Bad Request when adultCount < 1")
    void shouldReturn400WhenAdultCountZero() throws Exception {
        LocalDate checkIn = LocalDate.now().plusMonths(1);
        SupplierRateQueryRequest invalidRequest = SupplierRateQueryRequest.builder()
                .category(SupplierCategory.DISNEY)
                .checkInDate(checkIn)
                .checkOutDate(checkIn.plusDays(5))
                .adultCount(0) // Minimum is 1
                .build();

        mockMvc.perform(post("/api/v1/suppliers/rates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}