package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.domain.party.ClientProfile.AccountType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_ClientProfileController_getProfile extends Test_ClientProfileController {

    @Test
    @DisplayName("Happy Path: GET /api/v1/clients/{clientId} returns 200 OK with profile response")
    void shouldReturn200WithClientProfile() throws Exception {
        UUID clientId = UUID.randomUUID();
        ClientProfileResponse mockResponse = ClientProfileResponse.builder()
                .id(clientId)
                .accountType(AccountType.REGISTERED)
                .email("belle@villagelibrary.org")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .hasVaultedPaymentMethod(true)
                .companions(Collections.emptyList())
                .build();

        when(clientProfileService.getProfile(clientId)).thenReturn(mockResponse);

        mockMvc.perform(get("/api/v1/clients/{clientId}", clientId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.email").value("belle@villagelibrary.org"))
                .andExpect(jsonPath("$.firstName").value("Belle"))
                .andExpect(jsonPath("$.hasVaultedPaymentMethod").value(true));
    }

    @Test
    @DisplayName("Negative Scenario: GET /api/v1/clients/{clientId} returns 400 Bad Request when profile not found")
    void shouldReturn400WhenProfileNotFound() throws Exception {
        UUID missingId = UUID.randomUUID();
        when(clientProfileService.getProfile(missingId))
                .thenThrow(new IllegalArgumentException("Client profile not found: " + missingId));

        mockMvc.perform(get("/api/v1/clients/{clientId}", missingId))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Client profile not found: " + missingId));
    }
}