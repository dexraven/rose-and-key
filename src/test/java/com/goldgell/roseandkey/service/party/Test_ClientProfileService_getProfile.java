package com.goldgell.roseandkey.service.party;

import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

class Test_ClientProfileService_getProfile extends Test_ClientProfileService {

    @Test
    @DisplayName("Happy Path: Retrieves profile by ID and translates to response")
    void shouldReturnProfileWhenFound() {
        UUID id = UUID.randomUUID();
        ClientProfile profile = createMockProfile(id);
        ClientProfileResponse response = ClientProfileResponse.builder().id(id).build();

        when(clientProfileRepository.findById(id)).thenReturn(Optional.of(profile));
        when(clientProfileTranslator.toResponse(profile)).thenReturn(response);

        ClientProfileResponse result = clientProfileService.getProfile(id);
        assertThat(result.getId()).isEqualTo(id);
    }

    @Test
    @DisplayName("Negative Scenario: Throws IllegalArgumentException when ID is not found")
    void shouldThrowWhenNotFound() {
        UUID missingId = UUID.randomUUID();
        when(clientProfileRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> clientProfileService.getProfile(missingId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Client profile not found");
    }
}