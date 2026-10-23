package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.SupplierRateQueryRequest;
import com.goldgell.roseandkey.api.response.SupplierRateResponse;
import com.goldgell.roseandkey.service.supplier.SupplierRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/suppliers")
@RequiredArgsConstructor
public class SupplierGatewayController {

    private final SupplierRateService supplierRateService;

    @PostMapping("/rates")
    public ResponseEntity<List<SupplierRateResponse>> queryRates(
            @Valid @RequestBody SupplierRateQueryRequest request) {
        return ResponseEntity.ok(supplierRateService.getRates(request));
    }

    @PostMapping("/handoff/{intakeId}")
    public ResponseEntity<SupplierRateResponse> prepareHandoff(
            @PathVariable String intakeId,
            @Valid @RequestBody SupplierRateQueryRequest request) {
        return ResponseEntity.ok(supplierRateService.generateHandoffBridge(request, intakeId));
    }
}