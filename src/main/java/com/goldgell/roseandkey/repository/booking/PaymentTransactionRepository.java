package com.goldgell.roseandkey.repository.booking;

import com.goldgell.roseandkey.domain.booking.PaymentTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, UUID> {
    List<PaymentTransaction> findByBookingRecordIdOrderByCreatedAtDesc(UUID bookingRecordId);
    Optional<PaymentTransaction> findByGatewayTransactionId(String gatewayTransactionId);
}