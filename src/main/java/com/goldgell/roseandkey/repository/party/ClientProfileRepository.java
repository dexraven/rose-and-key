package com.goldgell.roseandkey.repository.party;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientProfileRepository extends JpaRepository<ClientProfile, UUID> {
    Optional<ClientProfile> findByEmailAndAccountType(String email, ClientProfile.AccountType accountType);
}