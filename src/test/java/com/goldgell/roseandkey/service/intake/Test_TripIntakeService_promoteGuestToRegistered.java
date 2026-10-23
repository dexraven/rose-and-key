package com.goldgell.roseandkey.service.intake;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

class Test_TripIntakeService_promoteGuestToRegistered extends Test_TripIntakeService {

    @Test
    @DisplayName("Happy Path: Promotes GUEST to REGISTERED and persists")
    void shouldPromoteGuestProfile() {
        UUID clientId = UUID.randomUUID();
        ClientProfile guest = createMockClientProfile(clientId, ClientProfile.AccountType.GUEST);

        when(clientProfileRepository.findById(clientId)).thenReturn(Optional.of(guest));

        tripIntakeService.promoteGuestToRegistered(clientId);

        assertThat(guest.getAccountType()).isEqualTo(ClientProfile.AccountType.REGISTERED);
        verify(clientProfileRepository).save(guest);
    }

    @Test
    @DisplayName("Idempotency: No-op if profile is already REGISTERED")
    void shouldBeNoOpIfAlreadyRegistered() {
        UUID clientId = UUID.randomUUID();
        ClientProfile registered = createMockClientProfile(clientId, ClientProfile.AccountType.REGISTERED);

        when(clientProfileRepository.findById(clientId)).thenReturn(Optional.of(registered));

        tripIntakeService.promoteGuestToRegistered(clientId);

        verify(clientProfileRepository, never()).save(any());
    }

    @Test
    @DisplayName("Negative Scenario: Throws IllegalArgumentException if profile does not exist")
    void shouldThrowWhenClientNotFound() {
        UUID missingId = UUID.randomUUID();
        when(clientProfileRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> tripIntakeService.promoteGuestToRegistered(missingId))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Client profile not found");

        verify(clientProfileRepository, never()).save(any());
    }
}