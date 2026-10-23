package com.goldgell.roseandkey.domain.party;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Test_ClientProfile_promoteGuestToRegistered extends Test_ClientProfile {

    @Test
    @DisplayName("Happy Path: Promotes GUEST to REGISTERED")
    void shouldPromoteGuestToRegistered() {
        ClientProfile profile = createDefaultProfile(ClientProfile.AccountType.GUEST);
        profile.promoteGuestToRegistered();

        assertThat(profile.getAccountType()).isEqualTo(ClientProfile.AccountType.REGISTERED);
    }

    @Test
    @DisplayName("Idempotency: Remains REGISTERED if called when already registered")
    void shouldRemainRegisteredIfAlreadyRegistered() {
        ClientProfile profile = createDefaultProfile(ClientProfile.AccountType.REGISTERED);
        profile.promoteGuestToRegistered();

        assertThat(profile.getAccountType()).isEqualTo(ClientProfile.AccountType.REGISTERED);
    }
}