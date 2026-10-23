package com.goldgell.roseandkey.domain.marketing;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class Test_MarketingOutreachRecommendation {

    @Test
    @DisplayName("POJO / Entity: Tests getters, setters, all-args, no-args, and mutation")
    void testPojoAndMutations() {
        MarketingOutreachRecommendation entity = new MarketingOutreachRecommendation();
        UUID id = UUID.randomUUID();
        ClientProfile client = new ClientProfile();
        LocalDate start = LocalDate.of(2027, 1, 15);
        LocalDate end = LocalDate.of(2027, 1, 22);
        Instant now = Instant.now();

        entity.setId(id);
        entity.setClient(client);
        entity.setRecommendedDestination(SupplierCategory.DISNEY);
        entity.setSuggestedWindowStart(start);
        entity.setSuggestedWindowEnd(end);
        entity.setTriggerReason("Child turning 10 anniversary");
        entity.setPriorityScore(95);
        entity.setStatus(MarketingOutreachRecommendation.OutreachStatus.PENDING_REVIEW);
        entity.setDismissedAt(now);
        entity.setContactedAt(now);
        entity.setCreatedAt(now);

        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getClient()).isEqualTo(client);
        assertThat(entity.getRecommendedDestination()).isEqualTo(SupplierCategory.DISNEY);
        assertThat(entity.getSuggestedWindowStart()).isEqualTo(start);
        assertThat(entity.getSuggestedWindowEnd()).isEqualTo(end);
        assertThat(entity.getTriggerReason()).isEqualTo("Child turning 10 anniversary");
        assertThat(entity.getPriorityScore()).isEqualTo(95);
        assertThat(entity.getStatus()).isEqualTo(MarketingOutreachRecommendation.OutreachStatus.PENDING_REVIEW);
        assertThat(entity.getDismissedAt()).isEqualTo(now);
        assertThat(entity.getContactedAt()).isEqualTo(now);
        assertThat(entity.getCreatedAt()).isEqualTo(now);

        MarketingOutreachRecommendation built = MarketingOutreachRecommendation.builder()
                .status(MarketingOutreachRecommendation.OutreachStatus.CONTACTED)
                .build();
        assertThat(built.getStatus()).isEqualTo(MarketingOutreachRecommendation.OutreachStatus.CONTACTED);
        assertThat(built.getCreatedAt()).isNotNull();
    }
}