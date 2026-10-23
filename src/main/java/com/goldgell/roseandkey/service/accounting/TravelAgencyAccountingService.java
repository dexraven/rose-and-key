package com.goldgell.roseandkey.service.accounting;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import com.goldgell.roseandkey.api.response.ExpenseDeductionResponse;
import com.goldgell.roseandkey.api.response.TaxEstimateReportResponse;
import com.goldgell.roseandkey.domain.accounting.CommissionLedgerEntry;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import com.goldgell.roseandkey.domain.booking.BookingRecord;
import com.goldgell.roseandkey.repository.accounting.CommissionLedgerRepository;
import com.goldgell.roseandkey.repository.accounting.ExpenseDeductionRepository;
import com.goldgell.roseandkey.translation.AccountingTranslator;
import com.goldgell.roseandkey.validation.AccountingValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TravelAgencyAccountingService {

    private static final Logger log = LoggerFactory.getLogger(TravelAgencyAccountingService.class);

    private static final double SELF_EMPLOYMENT_TAX_RATE = 0.153; // 15.3% FICA/Medicare
    private static final double ESTIMATED_FEDERAL_INCOME_BRACKET = 0.12; // Standard 12% marginal bracket

    private final CommissionLedgerRepository commissionLedgerRepository;
    private final ExpenseDeductionRepository expenseDeductionRepository;
    private final AccountingValidator accountingValidator;
    private final AccountingTranslator accountingTranslator;

    @Transactional
    public CommissionLedgerEntry recordBookingCommission(BookingRecord bookingRecord, double supplierRate, double hostAgencySplit) {
        long grossBookingCents = bookingRecord.getTotalCents();
        long grossCommission = Math.round(grossBookingCents * supplierRate);
        long netPayout = Math.round(grossCommission * hostAgencySplit);

        CommissionLedgerEntry entry = CommissionLedgerEntry.builder()
                .bookingRecord(bookingRecord)
                .grossBookingCents(grossBookingCents)
                .supplierCommissionRate(supplierRate)
                .grossCommissionCents(grossCommission)
                .hostAgencySplitRate(hostAgencySplit)
                .netAdvisorPayoutCents(netPayout)
                .status(CommissionLedgerEntry.PayoutStatus.PENDING_TRAVEL_COMPLETION)
                .expectedTravelDate(bookingRecord.getTripIntake().getEarliestDeparture())
                .build();

        log.info("Accrued Commission Ledger Entry for Booking [{}]: Gross Comm [{}], Advisor Net [{}]",
                bookingRecord.getId(), grossCommission, netPayout);
        return commissionLedgerRepository.save(entry);
    }

    @Transactional
    public ExpenseDeductionResponse logExpenseDeduction(ExpenseDeductionRequest request, String agentId) {
        accountingValidator.validateExpenseDeduction(request);
        ExpenseDeduction entity = accountingTranslator.toEntity(request, agentId);
        ExpenseDeduction saved = expenseDeductionRepository.save(entity);
        log.info("Logged Schedule C Expense [{}] for Agent [{}]", saved.getId(), agentId);
        return accountingTranslator.toResponse(saved);
    }

    @Transactional(readOnly = true)
    public TaxEstimateReportResponse computeQuarterlyTaxEstimate(String agentId, int year, int quarter) {
        accountingValidator.validateQuarter(quarter);

        LocalDate startDate = getQuarterStartDate(year, quarter);
        LocalDate endDate = getQuarterEndDate(year, quarter);

        List<ExpenseDeduction> expenses = expenseDeductionRepository.findByAgentIdAndExpenseDateBetween(agentId, startDate, endDate);
        long totalDeductionsCents = expenses.stream().mapToLong(ExpenseDeduction::getAmountCents).sum();

        List<CommissionLedgerEntry> commissions = commissionLedgerRepository.findByStatus(CommissionLedgerEntry.PayoutStatus.DISBURSED_TO_ADVISOR);
        long grossCommTotal = commissions.stream().mapToLong(CommissionLedgerEntry::getGrossCommissionCents).sum();
        long netAdvisorTotal = commissions.stream().mapToLong(CommissionLedgerEntry::getNetAdvisorPayoutCents).sum();

        long taxableSENet = Math.max(0, netAdvisorTotal - totalDeductionsCents);
        long seTax = Math.round((taxableSENet * 0.9235) * SELF_EMPLOYMENT_TAX_RATE);
        long fedTax = Math.round(taxableSENet * ESTIMATED_FEDERAL_INCOME_BRACKET);
        long totalQuarterlySetAside = seTax + fedTax;

        return TaxEstimateReportResponse.builder()
                .taxYear(year)
                .quarter(quarter)
                .grossCommissionEarningsCents(grossCommTotal)
                .netAdvisorPayoutCents(netAdvisorTotal)
                .totalDeductibleExpensesCents(totalDeductionsCents)
                .taxableSelfEmploymentNetCents(taxableSENet)
                .estimatedSelfEmploymentTaxCents(seTax)
                .estimatedFederalIncomeTaxCents(fedTax)
                .totalQuarterlySetAsideRecommendationCents(totalQuarterlySetAside)
                .build();
    }

    private LocalDate getQuarterStartDate(int year, int quarter) {
        return switch (quarter) {
            case 1 -> LocalDate.of(year, 1, 1);
            case 2 -> LocalDate.of(year, 4, 1);
            case 3 -> LocalDate.of(year, 6, 1);
            case 4 -> LocalDate.of(year, 9, 1);
            default -> throw new IllegalArgumentException("Invalid quarter: " + quarter);
        };
    }

    private LocalDate getQuarterEndDate(int year, int quarter) {
        return switch (quarter) {
            case 1 -> LocalDate.of(year, 3, 31);
            case 2 -> LocalDate.of(year, 5, 31);
            case 3 -> LocalDate.of(year, 8, 31);
            case 4 -> LocalDate.of(year, 12, 31);
            default -> throw new IllegalArgumentException("Invalid quarter: " + quarter);
        };
    }
}