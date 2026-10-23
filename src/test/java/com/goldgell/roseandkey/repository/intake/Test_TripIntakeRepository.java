package com.goldgell.roseandkey.repository.intake;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

@DataJpaTest
public abstract class Test_TripIntakeRepository {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected TripIntakeRepository tripIntakeRepository;

    protected ClientProfile client;

    @BeforeEach
    void setupClient() {
        client = entityManager.persistFlushFind(ClientProfile.builder()
                .accountType(ClientProfile.AccountType.GUEST)
                .email("traveler@example.com")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .build());
    }

    protected TripIntake persistIntake(String agentId, LocalDate dep, LocalDate ret) {
        TripIntake intake = TripIntake.builder()
                .client(client)
                .assignedAgentId(agentId)
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .earliestDeparture(dep)
                .latestReturn(ret)
                .partyHeadcount(2)
                .build();
        return entityManager.persistFlushFind(intake);
    }
}