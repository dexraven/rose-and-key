package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class VacationPredictionQueryRequest {
    @NotNull(message = "Destination category is required")
    private SupplierCategory destination;

    @Min(value = 2026, message = "Target year cannot be in the past")
    @Max(value = 2030, message = "Target year cannot exceed projection horizon")
    private int targetYear;
}