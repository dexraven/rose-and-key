package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.CompanionUpdateRequest;
import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.api.response.CompanionResponse;
import com.goldgell.roseandkey.domain.party.ClientProfile.AccountType;
import com.goldgell.roseandkey.domain.party.Companion.Relationship;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_ClientProfileController_addCompanion extends Test_ClientProfileController {

    @Test
    @DisplayName("Happy Path: POST /api/v1/clients/{clientId}/companions returns 200 OK with updated profile")
    void shouldReturn200WhenCompanionAdded() throws Exception {
        UUID clientId = UUID.randomUUID();
        UUID companionId = UUID.randomUUID();
        LocalDate dob = LocalDate.of(2014, 6, 15);

        CompanionUpdateRequest request = CompanionUpdateRequest.builder()
                .firstName("Chip")
                .lastName("Potts")
                .relationship(Relationship.CHILD)
                .dateOfBirth(dob)
                .build();

        CompanionResponse companionResponse = CompanionResponse.builder()
                .id(companionId)
                .firstName("Chip")
                .lastName("Potts")
                .relationship(Relationship.CHILD)
                .dateOfBirth(dob)
                .isMinor(true)
                .build();

        ClientProfileResponse response = ClientProfileResponse.builder()
                .id(clientId)
                .accountType(AccountType.REGISTERED)
                .email("belle@villagelibrary.org")
                .firstName("Belle")
                .lastName("French")
                .companions(List.of(companionResponse))
                .build();

        when(clientProfileService.addCompanion(eq(clientId), any(CompanionUpdateRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/clients/{clientId}/companions", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(clientId.toString()))
                .andExpect(jsonPath("$.companions[0].id").value(companionId.toString()))
                .andExpect(jsonPath("$.companions[0].firstName").value("Chip"))
                .andExpect(jsonPath("$.companions[0].minor").value(true));
    }

    @Test
    @DisplayName("Negative Scenario: POST /api/v1/clients/{clientId}/companions returns 400 Bad Request on blank names")
    void shouldReturn400OnValidationFailure() throws Exception {
        UUID clientId = UUID.randomUUID();
        CompanionUpdateRequest invalidRequest = CompanionUpdateRequest.builder()
                .firstName("") // Blank
                .lastName("")  // Blank
                .relationship(null) // Null
                .dateOfBirth(null)
                .build();

        mockMvc.perform(post("/api/v1/clients/{clientId}/companions", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }
}