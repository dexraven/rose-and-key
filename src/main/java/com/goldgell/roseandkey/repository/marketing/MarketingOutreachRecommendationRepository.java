package com.goldgell.roseandkey.repository.marketing;

import com.goldgell.roseandkey.domain.marketing.MarketingOutreachRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface MarketingOutreachRecommendationRepository extends JpaRepository<MarketingOutreachRecommendation, UUID> {
    List<MarketingOutreachRecommendation> findByClientIdOrderByPriorityScoreDesc(UUID clientId);
    List<MarketingOutreachRecommendation> findByStatusOrderByPriorityScoreDesc(MarketingOutreachRecommendation.OutreachStatus status);
}