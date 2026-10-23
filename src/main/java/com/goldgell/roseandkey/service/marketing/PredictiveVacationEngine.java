package com.goldgell.roseandkey.service.marketing;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.domain.marketing.TravelWindowForecast;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class PredictiveVacationEngine {

    public List<TravelWindowForecast> forecastBestTravelWindows(SupplierCategory destination, int targetYear) {
        if (destination == null) {
            throw new IllegalArgumentException("Destination category cannot be null.");
        }

        List<TravelWindowForecast> windows = new ArrayList<>();

        switch (destination) {
            case DISNEY -> {
                // Post-Marathon January Lull
                windows.add(TravelWindowForecast.builder()
                        .destination(destination)
                        .windowStart(LocalDate.of(targetYear, Month.JANUARY, 18))
                        .windowEnd(LocalDate.of(targetYear, Month.FEBRUARY, 6))
                        .crowdScore(3)
                        .priceRating("VALUE")
                        .historicalDiscountProbability(0.85)
                        .strategicNotes(List.of("Low standby wait times", "Post-marathon seasonal room promotions active"))
                        .build());

                // Early May Shoulder Season
                windows.add(TravelWindowForecast.builder()
                        .destination(destination)
                        .windowStart(LocalDate.of(targetYear, Month.MAY, 1))
                        .windowEnd(LocalDate.of(targetYear, Month.MAY, 20))
                        .crowdScore(4)
                        .priceRating("MODERATE")
                        .historicalDiscountProbability(0.65)
                        .strategicNotes(List.of("Mild Florida humidity", "Flower & Garden Festival optimal touring"))
                        .build());

                // Late August / September Low Season
                windows.add(TravelWindowForecast.builder()
                        .destination(destination)
                        .windowStart(LocalDate.of(targetYear, Month.AUGUST, 24))
                        .windowEnd(LocalDate.of(targetYear, Month.SEPTEMBER, 25))
                        .crowdScore(2)
                        .priceRating("VALUE")
                        .historicalDiscountProbability(0.92)
                        .strategicNotes(List.of("Lowest crowds of the year", "Halloween party seasonality"))
                        .build());
            }
            case UNIVERSAL -> {
                // Late September: Post-summer lull with Halloween Horror Nights
                windows.add(TravelWindowForecast.builder()
                        .destination(destination)
                        .windowStart(LocalDate.of(targetYear, Month.SEPTEMBER, 8))
                        .windowEnd(LocalDate.of(targetYear, Month.SEPTEMBER, 30))
                        .crowdScore(3)
                        .priceRating("VALUE")
                        .historicalDiscountProbability(0.78)
                        .strategicNotes(List.of("Low daytime standby lines", "HHN off-peak weeknight rates"))
                        .build());

                // Early November: Between HHN wrap and holiday surge
                windows.add(TravelWindowForecast.builder()
                        .destination(destination)
                        .windowStart(LocalDate.of(targetYear, Month.NOVEMBER, 3))
                        .windowEnd(LocalDate.of(targetYear, Month.NOVEMBER, 18))
                        .crowdScore(4)
                        .priceRating("MODERATE")
                        .historicalDiscountProbability(0.70)
                        .strategicNotes(List.of("Moderate park temperatures", "Epic Universe & Islands of Adventure low queues"))
                        .build());
            }
            case MARRIOTT -> {
                // Shoulder season resort promotions
                windows.add(TravelWindowForecast.builder()
                        .destination(destination)
                        .windowStart(LocalDate.of(targetYear, Month.OCTOBER, 15))
                        .windowEnd(LocalDate.of(targetYear, Month.NOVEMBER, 15))
                        .crowdScore(3)
                        .priceRating("MODERATE")
                        .historicalDiscountProbability(0.80)
                        .strategicNotes(List.of("FAM travel advisor rates active", "Autumn resort credit promos"))
                        .build());
            }
            default -> {
                return Collections.emptyList();
            }
        }

        return windows;
    }
}