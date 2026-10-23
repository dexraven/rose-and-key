package com.goldgell.roseandkey.repository.intake;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TripIntakeRepository extends JpaRepository<TripIntake, UUID> {
    List<TripIntake> findByAssignedAgentIdOrderByCreatedAtDesc(String assignedAgentId);
    List<TripIntake> findByClientIdOrderByCreatedAtDesc(UUID clientId);
}