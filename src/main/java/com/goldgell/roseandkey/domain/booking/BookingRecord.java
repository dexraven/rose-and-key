package com.goldgell.roseandkey.domain.booking;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "booking_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trip_intake_id", nullable = false)
    private TripIntake tripIntake;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "supplier_type", nullable = false, length = 32)
    private SupplierCategory supplierType;

    @Column(name = "supplier_confirmation_code", length = 100)
    private String supplierConfirmationCode;

    @Column(name = "package_reference_id", length = 100)
    private String packageReferenceId;

    @Min(0)
    @Column(name = "subtotal_cents", nullable = false)
    private long subtotalCents;

    @Min(0)
    @Column(name = "taxes_and_fees_cents", nullable = false)
    private long taxesAndFeesCents;

    @Min(0)
    @Column(name = "total_cents", nullable = false)
    private long totalCents;

    @Builder.Default
    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "USD";

    @Column(name = "confirmed_at")
    private Instant confirmedAt;

    @Builder.Default
    @OneToMany(mappedBy = "bookingRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentTransaction> transactions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public void addTransaction(@NotNull PaymentTransaction transaction) {
        transactions.add(transaction);
        transaction.setBookingRecord(this);
    }
}