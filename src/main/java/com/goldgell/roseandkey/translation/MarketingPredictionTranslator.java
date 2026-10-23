package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.VacationPredictionResponse;
import com.goldgell.roseandkey.domain.marketing.TravelWindowForecast;
import org.springframework.stereotype.Component;

@Component
public class MarketingPredictionTranslator {

    public VacationPredictionResponse toResponse(TravelWindowForecast forecast) {
        return VacationPredictionResponse.builder()
                .destination(forecast.getDestination())
                .windowStart(forecast.getWindowStart())
                .windowEnd(forecast.getWindowEnd())
                .crowdScore(forecast.getCrowdScore())
                .priceRating(forecast.getPriceRating())
                .historicalDiscountProbability(forecast.getHistoricalDiscountProbability())
                .strategicNotes(forecast.getStrategicNotes())
                .build();
    }
}