package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.LeadTravelerRequest;
import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.api.response.TripIntakeResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_TripIntakeController_submitIntake extends Test_TripIntakeController {

    @Test
    @DisplayName("Happy Path: Responds with 201 Created and persists intake")
    void shouldReturn201OnValidIntake() throws Exception {
        TripIntakeSubmissionRequest request = createValidPayload();
        UUID intakeId = UUID.randomUUID();
        UUID clientId = UUID.randomUUID();

        TripIntakeResponse response = TripIntakeResponse.builder()
                .intakeId(intakeId)
                .clientId(clientId)
                .accountType(ClientProfile.AccountType.GUEST)
                .leadTravelerName("Belle French")
                .leadTravelerEmail("belle@villagelibrary.org")
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .earliestDeparture(request.getEarliestDeparture())
                .latestReturn(request.getLatestReturn())
                .targetBudgetCents(600000L)
                .partyHeadcount(1)
                .build();

        when(tripIntakeService.submitIntake(any(TripIntakeSubmissionRequest.class), eq("agent-kelley-default")))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/intakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.intakeId").value(intakeId.toString()))
                .andExpect(jsonPath("$.leadTravelerName").value("Belle French"))
                .andExpect(jsonPath("$.status").value("INTAKE_SUBMITTED"));
    }

    @Test
    @DisplayName("Negative Scenario: Responds with 400 Bad Request when request body has invalid email format")
    void shouldReturn400OnInvalidEmail() throws Exception {
        LocalDate dep = LocalDate.now().plusMonths(2);
        TripIntakeSubmissionRequest badRequest = TripIntakeSubmissionRequest.builder()
                .leadTraveler(LeadTravelerRequest.builder()
                        .firstName("Belle")
                        .lastName("French")
                        .email("invalid-email-string")
                        .phoneE164("+12105550199")
                        .isGuest(true)
                        .build())
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .earliestDeparture(dep)
                .latestReturn(dep.plusDays(7))
                .targetBudgetCents(600000L)
                .partyHeadcount(1)
                .build();

        mockMvc.perform(post("/api/v1/intakes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"))
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("email")));
    }
}