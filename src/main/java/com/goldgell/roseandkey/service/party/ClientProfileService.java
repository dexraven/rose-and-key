package com.goldgell.roseandkey.service.party;

import com.goldgell.roseandkey.api.request.CompanionUpdateRequest;
import com.goldgell.roseandkey.api.request.PaymentVaultTokenRequest;
import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.Companion;
import com.goldgell.roseandkey.repository.party.ClientProfileRepository;
import com.goldgell.roseandkey.translation.ClientProfileTranslator;
import com.goldgell.roseandkey.validation.CompanionValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClientProfileService {

    private static final Logger log = LoggerFactory.getLogger(ClientProfileService.class);

    private final ClientProfileRepository clientProfileRepository;
    private final CompanionValidator companionValidator;
    private final ClientProfileTranslator clientProfileTranslator;

    @Transactional(readOnly = true)
    public ClientProfileResponse getProfile(UUID clientId) {
        ClientProfile client = findClientOrThrow(clientId);
        return clientProfileTranslator.toResponse(client);
    }

    @Transactional
    public ClientProfileResponse addCompanion(UUID clientId, CompanionUpdateRequest request) {
        companionValidator.validateUpdate(request);
        ClientProfile client = findClientOrThrow(clientId);

        Companion companion = Companion.builder()
                .firstName(request.getFirstName().trim())
                .lastName(request.getLastName().trim())
                .relationship(request.getRelationship())
                .dateOfBirth(request.getDateOfBirth())
                .build();

        client.addCompanion(companion);
        ClientProfile saved = clientProfileRepository.save(client);
        log.info("Appended companion [{}] to client [{}]", companion.getFirstName(), clientId);
        return clientProfileTranslator.toResponse(saved);
    }

    @Transactional
    public void updateVaultedPaymentTokens(UUID clientId, PaymentVaultTokenRequest request) {
        ClientProfile client = findClientOrThrow(clientId);
        client.setVaultedCustomerToken(request.getVaultedCustomerToken());
        client.setDefaultPaymentMethodToken(request.getDefaultPaymentMethodToken());
        clientProfileRepository.save(client);
        log.info("Updated vaulted tokens for client [{}]", clientId);
    }

    private ClientProfile findClientOrThrow(UUID clientId) {
        return clientProfileRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client profile not found: " + clientId));
    }
}