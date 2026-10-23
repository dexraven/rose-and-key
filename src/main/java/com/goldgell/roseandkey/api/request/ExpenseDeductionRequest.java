package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.accounting.ExpenseDeduction.ExpenseCategory;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ExpenseDeductionRequest {
    @NotBlank(message = "Expense description is required")
    private String description;

    @NotNull(message = "Expense category is required")
    private ExpenseCategory category;

    @Min(value = 1, message = "Expense amount must be strictly positive")
    private long amountCents;

    @NotNull(message = "Expense date is required")
    private LocalDate expenseDate;

    private String receiptDocumentUrl;
}