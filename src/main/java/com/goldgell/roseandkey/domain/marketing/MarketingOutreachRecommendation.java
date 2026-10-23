package com.goldgell.roseandkey.domain.marketing;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "marketing_outreach_recommendations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarketingOutreachRecommendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientProfile client;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SupplierCategory recommendedDestination;

    @Column(nullable = false)
    private LocalDate suggestedWindowStart;

    @Column(nullable = false)
    private LocalDate suggestedWindowEnd;

    @Column(nullable = false)
    private String triggerReason; // e.g., "Child turning 10 anniversary", "Past December vacation rhythm"

    @Column(nullable = false)
    private int priorityScore; // 1 to 100

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OutreachStatus status;

    private Instant dismissedAt;
    private Instant contactedAt;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();

    public enum OutreachStatus {
        PENDING_REVIEW,
        CONTACTED,
        CONVERTED_TO_INTAKE,
        DISMISSED
    }
}