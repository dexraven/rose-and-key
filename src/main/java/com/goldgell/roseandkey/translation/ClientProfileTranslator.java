package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.api.response.CompanionResponse;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.Companion;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Component
public class ClientProfileTranslator {

    public ClientProfileResponse toResponse(ClientProfile profile) {
        List<CompanionResponse> companionResponses = profile.getCompanions() != null
                ? profile.getCompanions().stream().map(this::toCompanionResponse).toList()
                : Collections.emptyList();

        return ClientProfileResponse.builder()
                .id(profile.getId())
                .accountType(profile.getAccountType())
                .email(profile.getEmail())
                .firstName(profile.getFirstName())
                .lastName(profile.getLastName())
                .phoneE164(profile.getPhoneE164())
                .hasVaultedPaymentMethod(profile.getDefaultPaymentMethodToken() != null && !profile.getDefaultPaymentMethodToken().isBlank())
                .companions(companionResponses)
                .build();
    }

    public CompanionResponse toCompanionResponse(Companion companion) {
        return CompanionResponse.builder()
                .id(companion.getId())
                .firstName(companion.getFirstName())
                .lastName(companion.getLastName())
                .relationship(companion.getRelationship())
                .dateOfBirth(companion.getDateOfBirth())
                .isMinor(companion.isMinorOnDate(LocalDate.now()))
                .build();
    }
}