package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.ExpenseDeductionRequest;
import com.goldgell.roseandkey.api.response.ExpenseDeductionResponse;
import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction;
import org.springframework.stereotype.Component;

@Component
public class AccountingTranslator {

    public ExpenseDeduction toEntity(ExpenseDeductionRequest request, String agentId) {
        return ExpenseDeduction.builder()
                .agentId(agentId)
                .expenseDescription(request.getDescription().trim())
                .category(request.getCategory())
                .amountCents(request.getAmountCents())
                .expenseDate(request.getExpenseDate())
                .receiptDocumentUrl(request.getReceiptDocumentUrl())
                .build();
    }

    public ExpenseDeductionResponse toResponse(ExpenseDeduction entity) {
        return ExpenseDeductionResponse.builder()
                .id(entity.getId())
                .agentId(entity.getAgentId())
                .description(entity.getExpenseDescription())
                .category(entity.getCategory())
                .amountCents(entity.getAmountCents())
                .expenseDate(entity.getExpenseDate())
                .receiptDocumentUrl(entity.getReceiptDocumentUrl())
                .build();
    }
}