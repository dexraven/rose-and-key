package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.CompanionUpdateRequest;
import com.goldgell.roseandkey.domain.party.Companion;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

public abstract class Test_CompanionValidator {

    protected CompanionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new CompanionValidator();
    }

    protected CompanionUpdateRequest createRequestWithDob(LocalDate dob) {
        return CompanionUpdateRequest.builder()
                .firstName("Maurice")
                .lastName("French")
                .relationship(Companion.Relationship.PARENT)
                .dateOfBirth(dob)
                .build();
    }
}