package com.goldgell.roseandkey.domain.party;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "client_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "account_type", nullable = false, length = 32)
    private AccountType accountType;

    @Email
    @NotBlank
    @Column(nullable = false, length = 255)
    private String email;

    @NotBlank
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank
    @Column(name = "phone_e164", nullable = false, length = 20)
    private String phoneE164;

    @Column(name = "vaulted_customer_token")
    private String vaultedCustomerToken;

    @Column(name = "default_payment_method_token")
    private String defaultPaymentMethodToken;

    @Builder.Default
    @OneToMany(mappedBy = "primaryClient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Companion> companions = new ArrayList<>();

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public void addCompanion(@NotNull Companion companion) {
        companions.add(companion);
        companion.setPrimaryClient(this);
    }

    public void promoteGuestToRegistered() {
        this.accountType = AccountType.REGISTERED;
    }

    public enum AccountType {
        GUEST,
        REGISTERED
    }
}