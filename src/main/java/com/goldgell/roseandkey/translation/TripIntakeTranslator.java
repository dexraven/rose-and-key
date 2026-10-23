package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.CompanionInputRequest;
import com.goldgell.roseandkey.api.request.LeadTravelerRequest;
import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.api.response.TripIntakeResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.ClientProfile.AccountType;
import com.goldgell.roseandkey.domain.party.Companion;
import org.springframework.stereotype.Component;

@Component
public class TripIntakeTranslator {

    public ClientProfile toClientProfileEntity(LeadTravelerRequest request) {
        return ClientProfile.builder()
                .accountType(request.isGuest() ? AccountType.GUEST : AccountType.REGISTERED)
                .email(request.getEmail().trim().toLowerCase())
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .phoneE164(request.getPhoneE164().trim())
                .build();
    }

    public Companion toCompanionEntity(CompanionInputRequest request) {
        return Companion.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .relationship(request.getRelationship())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    public TripIntake toTripIntakeEntity(TripIntakeSubmissionRequest request, ClientProfile client, String assignedAgentId) {
        return TripIntake.builder()
                .client(client)
                .assignedAgentId(assignedAgentId)
                .destinationTarget(request.getDestinationTarget())
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .earliestDeparture(request.getEarliestDeparture())
                .latestReturn(request.getLatestReturn())
                .targetBudgetCents(request.getTargetBudgetCents())
                .partyHeadcount(request.getPartyHeadcount())
                .specialNotes(request.getSpecialNotes())
                .build();
    }

    public TripIntakeResponse toResponse(TripIntake intake) {
        ClientProfile client = intake.getClient();
        return TripIntakeResponse.builder()
                .intakeId(intake.getId())
                .clientId(client.getId())
                .accountType(client.getAccountType())
                .leadTravelerName(client.getFirstName() + " " + client.getLastName())
                .leadTravelerEmail(client.getEmail())
                .destinationTarget(intake.getDestinationTarget())
                .status(intake.getStatus())
                .earliestDeparture(intake.getEarliestDeparture())
                .latestReturn(intake.getLatestReturn())
                .targetBudgetCents(intake.getTargetBudgetCents())
                .partyHeadcount(intake.getPartyHeadcount())
                .build();
    }
}