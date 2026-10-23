package com.goldgell.roseandkey.service.intake;

import com.goldgell.roseandkey.api.request.CompanionInputRequest;
import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.api.response.TripIntakeResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class Test_TripIntakeService_submitIntake extends Test_TripIntakeService {

    @Test
    @DisplayName("Happy Path: Creates profile, attaches companion, and persists trip intake")
    void shouldSuccessfullySubmitIntakeForNewGuest() {
        TripIntakeSubmissionRequest request = createValidSubmissionRequest(true);
        UUID clientId = UUID.randomUUID();
        UUID intakeId = UUID.randomUUID();

        ClientProfile profile = createMockClientProfile(clientId, ClientProfile.AccountType.GUEST);
        TripIntake intake = TripIntake.builder().id(intakeId).client(profile).build();
        TripIntakeResponse expectedResponse = TripIntakeResponse.builder()
                .intakeId(intakeId)
                .clientId(clientId)
                .build();

        Companion mockCompanion = Companion.builder()
                .firstName("Maurice")
                .lastName("French")
                .relationship(Companion.Relationship.PARENT)
                .build();

        doNothing().when(tripIntakeValidator).validateSubmission(request);
        when(clientProfileRepository.findByEmailAndAccountType("belle@villagelibrary.org", ClientProfile.AccountType.GUEST))
                .thenReturn(Optional.empty());
        when(tripIntakeTranslator.toClientProfileEntity(request.getLeadTraveler())).thenReturn(profile);
        when(tripIntakeTranslator.toCompanionEntity(any(CompanionInputRequest.class))).thenReturn(mockCompanion);
        when(tripIntakeTranslator.toTripIntakeEntity(request, profile, "agent-01")).thenReturn(intake);
        when(tripIntakeRepository.save(intake)).thenReturn(intake);
        when(tripIntakeTranslator.toResponse(intake)).thenReturn(expectedResponse);

        TripIntakeResponse response = tripIntakeService.submitIntake(request, "agent-01");

        assertThat(response).isNotNull();
        assertThat(response.getIntakeId()).isEqualTo(intakeId);
        verify(tripIntakeValidator).validateSubmission(request);
        verify(clientProfileRepository).save(profile);
        verify(tripIntakeRepository).save(intake);
        assertThat(profile.getCompanions()).contains(mockCompanion);
    }

    @Test
    @DisplayName("Edge Case: Submits intake when companions list is null without NullPointerException")
    void shouldHandleNullCompanionsGracefully() {
        TripIntakeSubmissionRequest base = createValidSubmissionRequest(false);
        TripIntakeSubmissionRequest request = TripIntakeSubmissionRequest.builder()
                .leadTraveler(base.getLeadTraveler())
                .destinationTarget(base.getDestinationTarget())
                .earliestDeparture(base.getEarliestDeparture())
                .latestReturn(base.getLatestReturn())
                .targetBudgetCents(base.getTargetBudgetCents())
                .partyHeadcount(1)
                .companions(null)
                .build();

        ClientProfile client = createMockClientProfile(UUID.randomUUID(), ClientProfile.AccountType.REGISTERED);
        TripIntake intake = TripIntake.builder().id(UUID.randomUUID()).client(client).build();

        doNothing().when(tripIntakeValidator).validateSubmission(request);
        when(clientProfileRepository.findByEmailAndAccountType(anyString(), any())).thenReturn(Optional.of(client));
        when(tripIntakeTranslator.toTripIntakeEntity(request, client, "agent-01")).thenReturn(intake);
        when(tripIntakeRepository.save(intake)).thenReturn(intake);
        when(tripIntakeTranslator.toResponse(intake)).thenReturn(TripIntakeResponse.builder().build());

        TripIntakeResponse res = tripIntakeService.submitIntake(request, "agent-01");
        assertThat(res).isNotNull();
    }

    @Test
    @DisplayName("Negative Scenario: Validator exception halts execution prior to repository interactions")
    void shouldHaltWhenValidationFails() {
        TripIntakeSubmissionRequest request = createValidSubmissionRequest(true);
        doThrow(new IllegalArgumentException("Date mismatch"))
                .when(tripIntakeValidator).validateSubmission(request);

        assertThatThrownBy(() -> tripIntakeService.submitIntake(request, "agent-01"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Date mismatch");

        verifyNoInteractions(clientProfileRepository);
        verifyNoInteractions(tripIntakeRepository);
    }
}