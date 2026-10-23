package com.goldgell.roseandkey.domain.accounting;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "commission_ledger_entries")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommissionLedgerEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_record_id", nullable = false)
    private BookingRecord bookingRecord;

    @Column(nullable = false)
    private long grossBookingCents;

    @Column(nullable = false)
    private double supplierCommissionRate; // e.g. 0.10 (10%)

    @Column(nullable = false)
    private long grossCommissionCents;

    @Column(nullable = false)
    private double hostAgencySplitRate; // e.g. 0.80 (80% to Kelley, 20% to Host)

    @Column(nullable = false)
    private long netAdvisorPayoutCents;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayoutStatus status;

    private LocalDate expectedTravelDate;
    private LocalDate actualDisbursementDate;

    public enum PayoutStatus {
        PENDING_TRAVEL_COMPLETION,
        RECEIVED_BY_HOST,
        DISBURSED_TO_ADVISOR
    }
}