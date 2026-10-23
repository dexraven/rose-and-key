package com.goldgell.roseandkey.repository.booking;

import com.goldgell.roseandkey.domain.booking.BookingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRecordRepository extends JpaRepository<BookingRecord, UUID> {
    List<BookingRecord> findByTripIntakeId(UUID tripIntakeId);
}