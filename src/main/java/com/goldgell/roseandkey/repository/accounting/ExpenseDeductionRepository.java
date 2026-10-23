package com.goldgell.roseandkey.repository.accounting;

import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseDeductionRepository extends JpaRepository<ExpenseDeduction, UUID> {
    List<ExpenseDeduction> findByAgentIdAndExpenseDateBetween(String agentId, LocalDate startDate, LocalDate endDate);
}