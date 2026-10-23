package com.goldgell.roseandkey.service.accounting;

import com.goldgell.roseandkey.repository.accounting.CommissionLedgerRepository;
import com.goldgell.roseandkey.repository.accounting.ExpenseDeductionRepository;
import com.goldgell.roseandkey.translation.AccountingTranslator;
import com.goldgell.roseandkey.validation.AccountingValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class Test_TravelAgencyAccountingService {

    @Mock
    protected CommissionLedgerRepository commissionLedgerRepository;

    @Mock
    protected ExpenseDeductionRepository expenseDeductionRepository;

    @Mock
    protected AccountingValidator accountingValidator;

    @Mock
    protected AccountingTranslator accountingTranslator;

    @InjectMocks
    protected TravelAgencyAccountingService accountingService;
}