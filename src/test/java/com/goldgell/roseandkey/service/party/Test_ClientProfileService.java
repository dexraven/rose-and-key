package com.goldgell.roseandkey.service.party;

import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.repository.party.ClientProfileRepository;
import com.goldgell.roseandkey.translation.ClientProfileTranslator;
import com.goldgell.roseandkey.validation.CompanionValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public abstract class Test_ClientProfileService {

    @Mock
    protected ClientProfileRepository clientProfileRepository;

    @Mock
    protected CompanionValidator companionValidator;

    @Mock
    protected ClientProfileTranslator clientProfileTranslator;

    @InjectMocks
    protected ClientProfileService clientProfileService;

    protected ClientProfile createMockProfile(UUID id) {
        return ClientProfile.builder()
                .id(id)
                .accountType(ClientProfile.AccountType.REGISTERED)
                .email("belle@castle.org")
                .firstName("Belle")
                .lastName("French")
                .phoneE164("+12105550199")
                .companions(new ArrayList<>())
                .build();
    }
}