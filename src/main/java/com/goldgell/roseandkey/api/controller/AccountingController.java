package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import com.goldgell.roseandkey.api.response.ExpenseDeductionResponse;
import com.goldgell.roseandkey.api.response.TaxEstimateReportResponse;
import com.goldgell.roseandkey.service.accounting.TravelAgencyAccountingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounting")
@RequiredArgsConstructor
public class AccountingController {

    private final TravelAgencyAccountingService accountingService;

    @PostMapping("/expenses")
    public ResponseEntity<ExpenseDeductionResponse> logExpense(
            @Valid @RequestBody ExpenseDeductionRequest request,
            @RequestHeader(name = "X-Agent-ID", defaultValue = "agent-kelley-default") String agentId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(accountingService.logExpenseDeduction(request, agentId));
    }

    @GetMapping("/taxes/quarterly")
    public ResponseEntity<TaxEstimateReportResponse> getQuarterlyTaxEstimate(
            @RequestParam int year,
            @RequestParam int quarter,
            @RequestHeader(name = "X-Agent-ID", defaultValue = "agent-kelley-default") String agentId) {
        return ResponseEntity.ok(accountingService.computeQuarterlyTaxEstimate(agentId, year, quarter));
    }
}