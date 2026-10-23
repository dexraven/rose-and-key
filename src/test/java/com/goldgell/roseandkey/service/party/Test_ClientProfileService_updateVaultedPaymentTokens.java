package com.goldgell.roseandkey.service.party;

import com.goldgell.roseandkey.api.request.PaymentVaultTokenRequest;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class Test_ClientProfileService_updateVaultedPaymentTokens extends Test_ClientProfileService {

    @Test
    @DisplayName("Happy Path: Updates vaulted customer and payment method tokens and persists")
    void shouldUpdateVaultedTokens() {
        UUID id = UUID.randomUUID();
        ClientProfile profile = createMockProfile(id);
        PaymentVaultTokenRequest request = PaymentVaultTokenRequest.builder()
                .vaultedCustomerToken("cus_vault_99")
                .defaultPaymentMethodToken("pm_card_99")
                .build();

        when(clientProfileRepository.findById(id)).thenReturn(Optional.of(profile));

        clientProfileService.updateVaultedPaymentTokens(id, request);

        assertThat(profile.getVaultedCustomerToken()).isEqualTo("cus_vault_99");
        assertThat(profile.getDefaultPaymentMethodToken()).isEqualTo("pm_card_99");
        verify(clientProfileRepository).save(profile);
    }
}