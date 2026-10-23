package com.goldgell.roseandkey.service.intake;

import com.goldgell.roseandkey.api.request.CompanionInputRequest;
import com.goldgell.roseandkey.api.request.LeadTravelerRequest;
import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.Companion;
import com.goldgell.roseandkey.repository.intake.TripIntakeRepository;
import com.goldgell.roseandkey.repository.party.ClientProfileRepository;
import com.goldgell.roseandkey.translation.TripIntakeTranslator;
import com.goldgell.roseandkey.validation.TripIntakeValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public abstract class Test_TripIntakeService {

    @Mock
    protected ClientProfileRepository clientProfileRepository;

    @Mock
    protected TripIntakeRepository tripIntakeRepository;

    @Mock
    protected TripIntakeValidator tripIntakeValidator;

    @Mock
    protected TripIntakeTranslator tripIntakeTranslator;

    @InjectMocks
    protected TripIntakeService tripIntakeService;

    protected TripIntakeSubmissionRequest createValidSubmissionRequest(boolean isGuest) {
        LocalDate departure = LocalDate.now().plusMonths(2);
        return TripIntakeSubmissionRequest.builder()
                .leadTraveler(LeadTravelerRequest.builder()
                        .firstName("Belle")
                        .lastName("French")
                        .email("belle@villagelibrary.org")
                        .phoneE164("+12105550199")
                        .isGuest(isGuest)
                        .build())
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .earliestDeparture(departure)
                .latestReturn(departure.plusDays(7))
                .targetBudgetCents(850000L)
                .partyHeadcount(2)
                .companions(List.of(
                        CompanionInputRequest.builder()
                                .firstName("Maurice")
                                .lastName("French")
                                .relationship(Companion.Relationship.PARENT)
                                .dateOfBirth(LocalDate.of(1965, 3, 10))
                                .build()
                ))
                .build();
    }

    protected ClientProfile createMockClientProfile(UUID id, ClientProfile.AccountType type) {
        return ClientProfile.builder()
                .id(id)
                .accountType(type)
                .email("belle@villagelibrary.org")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .build();
    }
}