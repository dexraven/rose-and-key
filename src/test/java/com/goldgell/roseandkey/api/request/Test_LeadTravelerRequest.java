package com.goldgell.roseandkey.api.request;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_LeadTravelerRequest {

    @Test
    @DisplayName("Contracts: Verifies equals, hashCode, toString, and builder values")
    void testValueContract() {
        LeadTravelerRequest req1 = LeadTravelerRequest.builder()
                .firstName("Belle")
                .lastName("French")
                .email("belle@villagelibrary.org")
                .phoneE164("+12105550199")
                .isGuest(true)
                .build();

        LeadTravelerRequest req2 = LeadTravelerRequest.builder()
                .firstName("Belle")
                .lastName("French")
                .email("belle@villagelibrary.org")
                .phoneE164("+12105550199")
                .isGuest(true)
                .build();

        LeadTravelerRequest different = LeadTravelerRequest.builder()
                .firstName("Adam")
                .lastName("Beast")
                .email("adam@castle.org")
                .phoneE164("+12105550100")
                .isGuest(false)
                .build();

        assertThat(req1).isEqualTo(req2);
        assertThat(req1.hashCode()).isEqualTo(req2.hashCode());
        assertThat(req1).isNotEqualTo(different);
        assertThat(req1.toString()).contains("Belle", "French");
        assertThat(req1.isGuest()).isTrue();
    }
}