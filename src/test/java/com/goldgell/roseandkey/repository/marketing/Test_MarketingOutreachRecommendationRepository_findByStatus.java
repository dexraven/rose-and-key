package com.goldgell.roseandkey.repository.marketing;

import com.goldgell.roseandkey.domain.marketing.MarketingOutreachRecommendation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class Test_MarketingOutreachRecommendationRepository_findByStatus extends Test_MarketingOutreachRecommendationRepository {

    @Test
    @DisplayName("Happy Path: Retrieves recommendations by review status ordered by priority descending")
    void shouldFindRecommendationsByStatus() {
        MarketingOutreachRecommendation pending1 = persistRecommendation(
                MarketingOutreachRecommendation.OutreachStatus.PENDING_REVIEW, 40);
        MarketingOutreachRecommendation pending2 = persistRecommendation(
                MarketingOutreachRecommendation.OutreachStatus.PENDING_REVIEW, 80);
        persistRecommendation(MarketingOutreachRecommendation.OutreachStatus.CONTACTED, 90);

        List<MarketingOutreachRecommendation> results = repository
                .findByStatusOrderByPriorityScoreDesc(MarketingOutreachRecommendation.OutreachStatus.PENDING_REVIEW);

        assertThat(results).hasSize(2);
        assertThat(results).extracting(MarketingOutreachRecommendation::getId)
                .containsExactly(pending2.getId(), pending1.getId());
    }
}