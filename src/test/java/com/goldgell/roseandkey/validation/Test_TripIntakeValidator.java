package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.CompanionInputRequest;
import com.goldgell.roseandkey.api.request.LeadTravelerRequest;
import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;

public abstract class Test_TripIntakeValidator {

    protected TripIntakeValidator validator;

    @BeforeEach
    void setUp() {
        validator = new TripIntakeValidator();
    }

    protected TripIntakeSubmissionRequest.TripIntakeSubmissionRequestBuilder createBaseRequestBuilder() {
        LocalDate futureDeparture = LocalDate.now().plusMonths(2);
        return TripIntakeSubmissionRequest.builder()
                .leadTraveler(LeadTravelerRequest.builder()
                        .firstName("Belle")
                        .lastName("French")
                        .email("belle@villagelibrary.org")
                        .phoneE164("+12105550199")
                        .isGuest(true)
                        .build())
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .earliestDeparture(futureDeparture)
                .latestReturn(futureDeparture.plusDays(7))
                .targetBudgetCents(850000L)
                .partyHeadcount(2)
                .companions(List.of(
                        CompanionInputRequest.builder()
                                .firstName("Maurice")
                                .lastName("French")
                                .relationship(Companion.Relationship.PARENT)
                                .dateOfBirth(LocalDate.of(1965, 3, 10))
                                .build()
                ));
    }
}