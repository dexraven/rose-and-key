package com.goldgell.roseandkey.api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_TripIntakeController_promoteGuestToRegistered extends Test_TripIntakeController {

    @Test
    @DisplayName("Happy Path: Responds with 204 No Content upon promotion")
    void shouldReturn204OnPromotion() throws Exception {
        UUID clientId = UUID.randomUUID();
        doNothing().when(tripIntakeService).promoteGuestToRegistered(clientId);

        mockMvc.perform(post("/api/v1/intakes/clients/{clientId}/promote", clientId))
                .andExpect(status().isNoContent());
    }
}