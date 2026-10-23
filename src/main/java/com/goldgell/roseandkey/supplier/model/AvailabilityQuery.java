package com.goldgell.roseandkey.supplier.model;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AvailabilityQuery {
    private SupplierCategory category;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int adultCount;
    private List<Integer> childAges;
    private String destinationCode;
    private String preferredResortTier;
}