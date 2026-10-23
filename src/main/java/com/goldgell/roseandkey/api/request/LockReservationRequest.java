package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class LockReservationRequest {
    @NotNull(message = "Trip intake ID is required")
    private UUID tripIntakeId;

    @NotNull(message = "Supplier category is required")
    private SupplierCategory category;

    @NotBlank(message = "Package ID is required")
    private String packageId;

    @Min(value = 0, message = "Subtotal cannot be negative")
    private long subtotalCents;

    @Min(value = 0, message = "Taxes and fees cannot be negative")
    private long taxesAndFeesCents;

    @Min(value = 0, message = "Total amount cannot be negative")
    private long totalCents;

    private String paymentMethodToken;
}