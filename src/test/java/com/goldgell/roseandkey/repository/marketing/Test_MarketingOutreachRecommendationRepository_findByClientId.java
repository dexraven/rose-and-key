package com.goldgell.roseandkey.repository.marketing;

import com.goldgell.roseandkey.domain.marketing.MarketingOutreachRecommendation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class Test_MarketingOutreachRecommendationRepository_findByClientId extends Test_MarketingOutreachRecommendationRepository {

    @Test
    @DisplayName("Happy Path: Retrieves recommendations for client ordered by priority score descending")
    void shouldFindRecommendationsByClientId() {
        MarketingOutreachRecommendation lowPriority = persistRecommendation(
                MarketingOutreachRecommendation.OutreachStatus.PENDING_REVIEW, 50);
        MarketingOutreachRecommendation highPriority = persistRecommendation(
                MarketingOutreachRecommendation.OutreachStatus.PENDING_REVIEW, 95);

        List<MarketingOutreachRecommendation> results = repository
                .findByClientIdOrderByPriorityScoreDesc(client.getId());

        assertThat(results).hasSize(2);
        assertThat(results).extracting(MarketingOutreachRecommendation::getId)
                .containsExactly(highPriority.getId(), lowPriority.getId());
    }

    @Test
    @DisplayName("Edge Case: Returns empty list when client has no recommendations")
    void shouldReturnEmptyForClientWithoutRecommendations() {
        List<MarketingOutreachRecommendation> results = repository
                .findByClientIdOrderByPriorityScoreDesc(UUID.randomUUID());
        assertThat(results).isEmpty();
    }
}