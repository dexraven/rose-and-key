package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BookingConfirmationResponse {
    private UUID bookingRecordId;
    private UUID tripIntakeId;
    private SupplierCategory supplierType;
    private String supplierConfirmationCode;
    private String packageReferenceId;
    private long totalCents;
    private String currency;
    private Instant confirmedAt;
    private String statusMessage;
}