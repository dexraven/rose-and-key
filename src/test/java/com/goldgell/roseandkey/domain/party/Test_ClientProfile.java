package com.goldgell.roseandkey.domain.party;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_ClientProfile {

    protected ClientProfile createDefaultProfile(ClientProfile.AccountType accountType) {
        return ClientProfile.builder()
                .id(UUID.randomUUID())
                .accountType(accountType)
                .email("belle@villagelibrary.org")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .vaultedCustomerToken("cus_vault_123")
                .defaultPaymentMethodToken("pm_card_456")
                .companions(new ArrayList<>())
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();
    }

    @Test
    @DisplayName("POJO / Entity: Verifies getters, setters, no-args, and mutation")
    void testPojoAndMutations() {
        ClientProfile profile = new ClientProfile();
        UUID id = UUID.randomUUID();
        Instant now = Instant.now();

        profile.setId(id);
        profile.setAccountType(ClientProfile.AccountType.GUEST);
        profile.setEmail("test@domain.com");
        profile.setFirstName("Maurice");
        profile.setLastName("French");
        profile.setPhoneE164("+15551234567");
        profile.setVaultedCustomerToken("cus_111");
        profile.setDefaultPaymentMethodToken("pm_222");
        profile.setCompanions(new ArrayList<>());
        profile.setCreatedAt(now);
        profile.setUpdatedAt(now);

        assertThat(profile.getId()).isEqualTo(id);
        assertThat(profile.getAccountType()).isEqualTo(ClientProfile.AccountType.GUEST);
        assertThat(profile.getEmail()).isEqualTo("test@domain.com");
        assertThat(profile.getFirstName()).isEqualTo("Maurice");
        assertThat(profile.getLastName()).isEqualTo("French");
        assertThat(profile.getPhoneE164()).isEqualTo("+15551234567");
        assertThat(profile.getVaultedCustomerToken()).isEqualTo("cus_111");
        assertThat(profile.getDefaultPaymentMethodToken()).isEqualTo("pm_222");
        assertThat(profile.getCompanions()).isEmpty();
        assertThat(profile.getCreatedAt()).isEqualTo(now);
        assertThat(profile.getUpdatedAt()).isEqualTo(now);
    }
}