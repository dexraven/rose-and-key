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
public class QuoteRateOption {
    private String packageId;
    private String supplierName;
    private String displayName;
    private long totalCents;
    private String currency;
    private boolean immediateBookingAvailable;
    private String deepLinkHandoffUrl;
    private Map<String, String> autoFillPayload;
}