package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import org.springframework.stereotype.Component;

@Component
public class SupplierRateTranslator {

    public AvailabilityQuery toAvailabilityQuery(SupplierRateQueryRequest request) {
        return AvailabilityQuery.builder()
                .category(request.getCategory())
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .adultCount(request.getAdultCount())
                .childAges(request.getChildAges())
                .destinationCode(request.getDestinationCode())
                .preferredResortTier(request.getPreferredResortTier())
                .build();
    }

    public SupplierRateResponse toResponse(QuoteRateOption option) {
        return SupplierRateResponse.builder()
                .packageId(option.getPackageId())
                .supplierName(option.getSupplierName())
                .displayName(option.getDisplayName())
                .totalCents(option.getTotalCents())
                .currency(option.getCurrency())
                .immediateBookingAvailable(option.isImmediateBookingAvailable())
                .deepLinkHandoffUrl(option.getDeepLinkHandoffUrl())
                .autoFillPayload(option.getAutoFillPayload())
                .build();
    }
}