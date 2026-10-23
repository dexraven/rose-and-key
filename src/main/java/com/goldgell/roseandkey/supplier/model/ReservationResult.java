package com.goldgell.roseandkey.supplier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ReservationResult {
    private boolean success;
    private String supplierConfirmationCode;
    private String bookingReference;
    private String statusMessage;
    private Map<String, Object> vendorMetadata;
}