package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.api.request.LeadTravelerRequest;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Test_TripIntakeTranslator_toClientProfileEntity extends Test_TripIntakeTranslator {

    @Test
    @DisplayName("Happy Path: Maps lead traveler and cleanses string boundaries")
    void shouldMapAndSanitizeProfile() {
        LeadTravelerRequest req = LeadTravelerRequest.builder()
                .firstName("  Belle ")
                .lastName(" French  ")
                .email(" Belle.French@Village.ORG ")
                .phoneE164(" +12105550199 ")
                .isGuest(true)
                .build();

        ClientProfile profile = translator.toClientProfileEntity(req);

        assertThat(profile.getAccountType()).isEqualTo(ClientProfile.AccountType.GUEST);
        assertThat(profile.getEmail()).isEqualTo("belle.french@village.org");
        assertThat(profile.getFirstName()).isEqualTo("Belle");
        assertThat(profile.getLastName()).isEqualTo("French");
        assertThat(profile.getPhoneE164()).isEqualTo("+12105550199");
    }

    @Test
    @DisplayName("Mutation: Maps REGISTERED account type when isGuest is false")
    void shouldMapRegisteredAccountType() {
        LeadTravelerRequest req = LeadTravelerRequest.builder()
                .firstName("Belle")
                .lastName("French")
                .email("belle@village.org")
                .phoneE164("+12105550199")
                .isGuest(false)
                .build();

        ClientProfile profile = translator.toClientProfileEntity(req);
        assertThat(profile.getAccountType()).isEqualTo(ClientProfile.AccountType.REGISTERED);
    }
}