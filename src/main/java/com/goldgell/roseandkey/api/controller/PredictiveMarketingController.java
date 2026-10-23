package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.VacationPredictionQueryRequest;
import com.goldgell.roseandkey.api.response.VacationPredictionResponse;
import com.goldgell.roseandkey.domain.marketing.TravelWindowForecast;
import com.goldgell.roseandkey.service.marketing.PredictiveVacationEngine;
import com.goldgell.roseandkey.translation.MarketingPredictionTranslator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/marketing/predictive")
@RequiredArgsConstructor
public class PredictiveMarketingController {

    private final PredictiveVacationEngine predictiveVacationEngine;
    private final MarketingPredictionTranslator marketingPredictionTranslator;

    @PostMapping("/windows")
    public ResponseEntity<List<VacationPredictionResponse>> getOptimalTravelWindows(
            @Valid @RequestBody VacationPredictionQueryRequest request) {
        List<TravelWindowForecast> forecasts = predictiveVacationEngine.forecastBestTravelWindows(
                request.getDestination(), request.getTargetYear());
        List<VacationPredictionResponse> responses = forecasts.stream()
                .map(marketingPredictionTranslator::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}