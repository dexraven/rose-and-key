package com.goldgell.roseandkey.domain.intake;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "trip_intakes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripIntake {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false)
    private ClientProfile client;

    @NotBlank
    @Column(name = "assigned_agent_id", nullable = false, length = 100)
    private String assignedAgentId;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "destination_target", nullable = false, length = 32)
    private SupplierCategory destinationTarget;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private TripStatus status;

    @NotNull
    @Column(name = "earliest_departure", nullable = false)
    private LocalDate earliestDeparture;

    @NotNull
    @Column(name = "latest_return", nullable = false)
    private LocalDate latestReturn;

    @Min(0)
    @Column(name = "target_budget_cents", nullable = false)
    private long targetBudgetCents;

    @Builder.Default
    @Column(name = "preferred_currency", nullable = false, length = 3)
    private String preferredCurrency = "USD";

    @Min(1)
    @Column(name = "party_headcount", nullable = false)
    private int partyHeadcount;

    @Column(name = "special_notes")
    private String specialNotes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public enum TripStatus {
        INTAKE_SUBMITTED,
        QUOTED,
        BOOKING_IN_PROGRESS,
        CONFIRMED,
        CANCELLED
    }

    public enum SupplierCategory {
        DISNEY,
        UNIVERSAL,
        CRUISE,
        MARRIOTT,
        OTHER
    }
}