package com.goldgell.roseandkey.domain.booking;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "booking_record_id", nullable = false)
    private BookingRecord bookingRecord;

    @NotBlank
    @Column(name = "gateway_transaction_id", nullable = false, length = 255)
    private String gatewayTransactionId;

    @NotBlank
    @Column(name = "gateway_payment_method_token", nullable = false, length = 255)
    private String gatewayPaymentMethodToken;

    @Min(0)
    @Column(name = "amount_cents", nullable = false)
    private long amountCents;

    @Builder.Default
    @Column(name = "currency", nullable = false, length = 3)
    private String currency = "USD";

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private PaymentStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    public enum PaymentStatus {
        PENDING_TOKENIZATION,
        AUTHORIZED,
        CAPTURED,
        FAILED,
        REFUNDED
    }
}