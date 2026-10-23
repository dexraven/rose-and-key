package com.goldgell.roseandkey.repository.accounting;

import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

@DataJpaTest
public abstract class Test_ExpenseDeductionRepository {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected ExpenseDeductionRepository expenseDeductionRepository;

    protected ExpenseDeduction persistExpense(String agentId, LocalDate date, long amountCents) {
        ExpenseDeduction deduction = ExpenseDeduction.builder()
                .agentId(agentId)
                .expenseDescription("Agency business expense")
                .category(ExpenseDeduction.ExpenseCategory.OFFICE_SUPPLIES)
                .amountCents(amountCents)
                .expenseDate(date)
                .build();
        return entityManager.persistFlushFind(deduction);
    }
}