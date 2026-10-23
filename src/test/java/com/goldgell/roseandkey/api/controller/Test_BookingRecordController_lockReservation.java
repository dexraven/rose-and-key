package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.LockReservationRequest;
import com.goldgell.roseandkey.api.response.BookingConfirmationResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_BookingRecordController_lockReservation extends Test_BookingRecordController {

    @Test
    @DisplayName("POST /api/v1/bookings/lock returns 201 Created on valid request")
    void shouldLockReservationSuccessfully() throws Exception {
        UUID bookingId = UUID.randomUUID();
        LockReservationRequest request = LockReservationRequest.builder()
                .tripIntakeId(UUID.randomUUID())
                .category(SupplierCategory.DISNEY)
                .packageId("PKG-001")
                .subtotalCents(100000L)
                .taxesAndFeesCents(10000L)
                .totalCents(110000L)
                .build();

        BookingConfirmationResponse response = BookingConfirmationResponse.builder()
                .bookingRecordId(bookingId)
                .supplierConfirmationCode("DSN-999")
                .build();

        when(bookingRecordService.lockAndConfirmBooking(any(LockReservationRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/bookings/lock")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingRecordId").value(bookingId.toString()))
                .andExpect(jsonPath("$.supplierConfirmationCode").value("DSN-999"));
    }
}