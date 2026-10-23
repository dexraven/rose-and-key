package com.goldgell.roseandkey.service.party;

import com.goldgell.roseandkey.api.request.CompanionUpdateRequest;
import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class Test_ClientProfileService_addCompanion extends Test_ClientProfileService {

    @Test
    @DisplayName("Happy Path: Validates companion, appends to profile, and persists")
    void shouldAddCompanionSuccessfully() {
        UUID id = UUID.randomUUID();
        ClientProfile profile = createMockProfile(id);
        CompanionUpdateRequest request = CompanionUpdateRequest.builder()
                .firstName("Maurice")
                .lastName("French")
                .relationship(Companion.Relationship.PARENT)
                .dateOfBirth(LocalDate.of(1965, 3, 10))
                .build();

        doNothing().when(companionValidator).validateUpdate(request);
        when(clientProfileRepository.findById(id)).thenReturn(Optional.of(profile));
        when(clientProfileRepository.save(any(ClientProfile.class))).thenReturn(profile);
        when(clientProfileTranslator.toResponse(profile)).thenReturn(ClientProfileResponse.builder().id(id).build());

        ClientProfileResponse response = clientProfileService.addCompanion(id, request);

        assertThat(response).isNotNull();
        verify(companionValidator).validateUpdate(request);
        verify(clientProfileRepository).save(profile);
        assertThat(profile.getCompanions()).hasSize(1);
    }
}