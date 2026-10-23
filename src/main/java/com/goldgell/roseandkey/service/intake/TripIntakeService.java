package com.goldgell.roseandkey.service.intake;

import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.api.response.TripIntakeResponse;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.ClientProfile.AccountType;
import com.goldgell.roseandkey.repository.intake.TripIntakeRepository;
import com.goldgell.roseandkey.repository.party.ClientProfileRepository;
import com.goldgell.roseandkey.translation.TripIntakeTranslator;
import com.goldgell.roseandkey.validation.TripIntakeValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TripIntakeService {

    private final ClientProfileRepository clientProfileRepository;
    private final TripIntakeRepository tripIntakeRepository;
    private final TripIntakeValidator tripIntakeValidator;
    private final TripIntakeTranslator tripIntakeTranslator;

    @Transactional
    public TripIntakeResponse submitIntake(TripIntakeSubmissionRequest request, String assignedAgentId) {
        tripIntakeValidator.validateSubmission(request);

        String cleanEmail = request.getLeadTraveler().getEmail().trim().toLowerCase();
        AccountType targetType = request.getLeadTraveler().isGuest() ? AccountType.GUEST : AccountType.REGISTERED;

        ClientProfile client = clientProfileRepository.findByEmailAndAccountType(cleanEmail, targetType)
                .orElseGet(() -> tripIntakeTranslator.toClientProfileEntity(request.getLeadTraveler()));

        if (request.getCompanions() != null) {
            request.getCompanions().forEach(c -> client.addCompanion(tripIntakeTranslator.toCompanionEntity(c)));
        }

        clientProfileRepository.save(client);

        TripIntake intake = tripIntakeTranslator.toTripIntakeEntity(request, client, assignedAgentId);
        TripIntake savedIntake = tripIntakeRepository.save(intake);

        log.info("Persisted Trip Intake [{}] for Client [{}]", savedIntake.getId(), client.getId());
        return tripIntakeTranslator.toResponse(savedIntake);
    }

    @Transactional
    public void promoteGuestToRegistered(UUID clientId) {
        ClientProfile client = clientProfileRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client profile not found: " + clientId));

        if (client.getAccountType() == AccountType.REGISTERED) {
            return;
        }

        client.promoteGuestToRegistered();
        clientProfileRepository.save(client);
        log.info("Promoted Client [{}] from GUEST to REGISTERED", clientId);
    }
}