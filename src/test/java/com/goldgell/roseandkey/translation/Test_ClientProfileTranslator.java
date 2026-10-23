package com.goldgell.roseandkey.translation;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Test_ClientProfileTranslator {

    protected ClientProfileTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new ClientProfileTranslator();
    }

    protected Companion createCompanion(String firstName, LocalDate dob) {
        return Companion.builder()
                .id(UUID.randomUUID())
                .firstName(firstName)
                .lastName("French")
                .relationship(Companion.Relationship.CHILD)
                .dateOfBirth(dob)
                .build();
    }

    protected ClientProfile createClientProfile(boolean withVaultedToken) {
        return ClientProfile.builder()
                .id(UUID.randomUUID())
                .accountType(ClientProfile.AccountType.REGISTERED)
                .email("belle@castle.org")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .defaultPaymentMethodToken(withVaultedToken ? "pm_token_123" : null)
                .companions(new ArrayList<>())
                .build();
    }
}