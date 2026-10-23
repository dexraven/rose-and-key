package com.goldgell.roseandkey.api.response;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class SupplierRateResponse {
    private String packageId;
    private String supplierName;
    private String displayName;
    private long totalCents;
    private String currency;
    private boolean immediateBookingAvailable;
    private String deepLinkHandoffUrl;
    private Map<String, String> autoFillPayload;
}