package com.goldgell.roseandkey.supplier.gateway;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import com.goldgell.roseandkey.supplier.model.QuoteRateOption;
import com.goldgell.roseandkey.supplier.model.ReservationResult;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class SupplierGateway {

    private final Map<SupplierCategory, SupplierAdapter> adapterRegistry = new EnumMap<>(SupplierCategory.class);

    public SupplierGateway(List<SupplierAdapter> adapters) {
        if (adapters != null) {
            for (SupplierAdapter adapter : adapters) {
                adapterRegistry.put(adapter.getSupportedCategory(), adapter);
            }
        }
    }

    public List<QuoteRateOption> queryRates(AvailabilityQuery query) {
        if (query == null || query.getCategory() == null) {
            throw new IllegalArgumentException("Query and target category cannot be null");
        }
        return resolveAdapter(query.getCategory()).fetchRatesAndAvailability(query);
    }

    public ReservationResult executeHoldOrLock(SupplierCategory category, LockReservationCommand command) {
        if (category == null || command == null) {
            throw new IllegalArgumentException("Category and command cannot be null");
        }
        return resolveAdapter(category).lockReservation(command);
    }

    public QuoteRateOption prepareAgentHandoff(SupplierCategory category, AvailabilityQuery query, String intakeId) {
        if (category == null || query == null || intakeId == null || intakeId.isBlank()) {
            throw new IllegalArgumentException("Category, query, and valid intake ID are required");
        }
        return resolveAdapter(category).generateAutoFillHandoff(query, intakeId);
    }

    private SupplierAdapter resolveAdapter(SupplierCategory category) {
        return Optional.ofNullable(adapterRegistry.get(category))
                .orElseThrow(() -> new UnsupportedOperationException("No supplier adapter registered for category: " + category));
    }
}