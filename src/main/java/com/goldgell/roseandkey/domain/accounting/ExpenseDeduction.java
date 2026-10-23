package com.goldgell.roseandkey.domain.accounting;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "expense_deductions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseDeduction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String agentId;

    @Column(nullable = false)
    private String expenseDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExpenseCategory category;

    @Column(nullable = false)
    private long amountCents;

    @Column(nullable = false)
    private LocalDate expenseDate;

    private String receiptDocumentUrl;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public enum ExpenseCategory {
        FAM_TRAVEL_AND_LODGING,
        ANNUAL_PASS_AND_PARK_TICKETS,
        CLIENT_GIFTS_AND_PROMOS,
        SOFTWARE_SUBSCRIPTIONS,
        OFFICE_SUPPLIES,
        PROFESSIONAL_DUES_AND_FEES,
        CONTINUING_EDUCATION
    }
}