package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.VacationPredictionQueryRequest;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_PredictiveMarketingController_getOptimalTravelWindows extends Test_PredictiveMarketingController {

    @Test
    @DisplayName("POST /api/v1/marketing/predictive/windows returns 200 OK")
    void shouldReturn200WithWindows() throws Exception {
        VacationPredictionQueryRequest request = VacationPredictionQueryRequest.builder()
                .destination(SupplierCategory.DISNEY)
                .targetYear(2027)
                .build();

        when(predictiveVacationEngine.forecastBestTravelWindows(SupplierCategory.DISNEY, 2027))
                .thenReturn(Collections.emptyList());

        mockMvc.perform(post("/api/v1/marketing/predictive/windows")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }
}