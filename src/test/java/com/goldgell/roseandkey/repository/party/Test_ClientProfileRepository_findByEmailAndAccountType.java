package com.goldgell.roseandkey.repository.party;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class Test_ClientProfileRepository_findByEmailAndAccountType extends Test_ClientProfileRepository {

    @Test
    @DisplayName("Happy Path: Finds profile by normalized email and matching account type")
    void shouldFindProfileByEmailAndType() {
        persistProfile("belle@villagelibrary.org", ClientProfile.AccountType.GUEST);

        Optional<ClientProfile> found = clientProfileRepository
                .findByEmailAndAccountType("belle@villagelibrary.org", ClientProfile.AccountType.GUEST);

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("belle@villagelibrary.org");
        assertThat(found.get().getAccountType()).isEqualTo(ClientProfile.AccountType.GUEST);
    }

    @Test
    @DisplayName("Edge Case: Returns empty when email matches but account type differs")
    void shouldReturnEmptyWhenAccountTypeDiffers() {
        persistProfile("belle@villagelibrary.org", ClientProfile.AccountType.GUEST);

        Optional<ClientProfile> found = clientProfileRepository
                .findByEmailAndAccountType("belle@villagelibrary.org", ClientProfile.AccountType.REGISTERED);

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Negative Scenario: Returns empty when email does not exist")
    void shouldReturnEmptyWhenNotFound() {
        Optional<ClientProfile> found = clientProfileRepository
                .findByEmailAndAccountType("missing@villagelibrary.org", ClientProfile.AccountType.GUEST);

        assertThat(found).isEmpty();
    }
}