package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.api.response.BookingConfirmationResponse;
import com.goldgell.roseandkey.service.booking.BookingRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingRecordController {

    private final BookingRecordService bookingRecordService;

    @PostMapping("/lock")
    public ResponseEntity<BookingConfirmationResponse> lockReservation(
            @Valid @RequestBody LockReservationRequest request) {
        BookingConfirmationResponse response = bookingRecordService.lockAndConfirmBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}