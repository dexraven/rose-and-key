package com.goldgell.roseandkey.repository.marketing;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.domain.marketing.MarketingOutreachRecommendation;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;

@DataJpaTest
public abstract class Test_MarketingOutreachRecommendationRepository {

    @Autowired
    protected TestEntityManager entityManager;

    @Autowired
    protected MarketingOutreachRecommendationRepository repository;

    protected ClientProfile client;

    @BeforeEach
    void setupClient() {
        client = entityManager.persistFlushFind(ClientProfile.builder()
                .accountType(ClientProfile.AccountType.REGISTERED)
                .email("marketing.target@example.com")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .build());
    }

    protected MarketingOutreachRecommendation persistRecommendation(
            MarketingOutreachRecommendation.OutreachStatus status, int priorityScore) {
        MarketingOutreachRecommendation recommendation = MarketingOutreachRecommendation.builder()
                .client(client)
                .recommendedDestination(SupplierCategory.DISNEY)
                .suggestedWindowStart(LocalDate.now().plusMonths(3))
                .suggestedWindowEnd(LocalDate.now().plusMonths(3).plusDays(7))
                .triggerReason("Annual vacation cadence")
                .priorityScore(priorityScore)
                .status(status)
                .build();
        return entityManager.persistFlushFind(recommendation);
    }
}