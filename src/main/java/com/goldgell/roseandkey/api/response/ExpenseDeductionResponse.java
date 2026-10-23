package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ExpenseDeductionResponse {
    private UUID id;
    private String agentId;
    private String description;
    private ExpenseCategory category;
    private long amountCents;
    private LocalDate expenseDate;
    private String receiptDocumentUrl;
}