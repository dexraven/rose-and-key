package com.goldgell.roseandkey.service.supplier;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.supplier.gateway.SupplierGateway;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import com.goldgell.roseandkey.translation.SupplierRateTranslator;
import com.goldgell.roseandkey.validation.SupplierCommandValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierRateService {

    private final SupplierGateway supplierGateway;
    private final SupplierCommandValidator supplierCommandValidator;
    private final SupplierRateTranslator supplierRateTranslator;

    public List<SupplierRateResponse> getRates(SupplierRateQueryRequest request) {
        supplierCommandValidator.validateRateQuery(request);
        AvailabilityQuery query = supplierRateTranslator.toAvailabilityQuery(request);
        List<QuoteRateOption> rates = supplierGateway.queryRates(query);
        return rates.stream().map(supplierRateTranslator::toResponse).toList();
    }

    public SupplierRateResponse generateHandoffBridge(SupplierRateQueryRequest request, String intakeId) {
        supplierCommandValidator.validateRateQuery(request);
        AvailabilityQuery query = supplierRateTranslator.toAvailabilityQuery(request);
        QuoteRateOption handoff = supplierGateway.prepareAgentHandoff(request.getCategory(), query, intakeId);
        return supplierRateTranslator.toResponse(handoff);
    }
}