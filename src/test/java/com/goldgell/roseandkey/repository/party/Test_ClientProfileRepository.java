package com.goldgell.roseandkey.repository.party;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
public abstract class Test_ClientProfileRepository {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected ClientProfileRepository clientProfileRepository;

    protected ClientProfile persistProfile(String email, ClientProfile.AccountType accountType) {
        ClientProfile profile = ClientProfile.builder()
                .email(email)
                .accountType(accountType)
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .build();
        return entityManager.persistFlushFind(profile);
    }
}