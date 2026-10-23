package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Test_ClientProfileTranslator_toResponse extends Test_ClientProfileTranslator {

    @Test
    @DisplayName("Happy Path: Converts client profile and companion children to full response")
    void shouldConvertProfileAndCompanions() {
        ClientProfile profile = createClientProfile(true);
        Companion comp = createCompanion("Chip", LocalDate.now().minusYears(10));
        profile.setCompanions(List.of(comp));

        ClientProfileResponse response = translator.toResponse(profile);

        assertThat(response.getId()).isEqualTo(profile.getId());
        assertThat(response.getEmail()).isEqualTo("belle@castle.org");
        assertThat(response.isHasVaultedPaymentMethod()).isTrue();
        assertThat(response.getCompanions()).hasSize(1);
        assertThat(response.getCompanions().getFirst().getFirstName()).isEqualTo("Chip");
    }

    @Test
    @DisplayName("Mutation: Returns false for hasVaultedPaymentMethod when token is null or blank")
    void shouldHandleNullOrBlankToken() {
        ClientProfile profile = createClientProfile(false);
        assertThat(translator.toResponse(profile).isHasVaultedPaymentMethod()).isFalse();

        profile.setDefaultPaymentMethodToken("   ");
        assertThat(translator.toResponse(profile).isHasVaultedPaymentMethod()).isFalse();
    }

    @Test
    @DisplayName("Edge Case: Returns empty companion list when domain companions is null")
    void shouldHandleNullCompanionsSafely() {
        ClientProfile profile = createClientProfile(false);
        profile.setCompanions(null);

        ClientProfileResponse response = translator.toResponse(profile);
        assertThat(response.getCompanions()).isEmpty();
    }
}