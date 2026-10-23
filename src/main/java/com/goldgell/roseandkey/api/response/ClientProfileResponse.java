package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.party.ClientProfile.AccountType;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ClientProfileResponse {
    private UUID id;
    private AccountType accountType;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneE164;
    private boolean hasVaultedPaymentMethod;
    private List<CompanionResponse> companions;
}