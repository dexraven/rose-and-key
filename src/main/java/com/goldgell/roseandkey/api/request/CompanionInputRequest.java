package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.party.Companion.Relationship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;

@Value
@Builder
@Jacksonized
public class CompanionInputRequest {

    @NotBlank(message = "Companion first name is required")
    String firstName;

    @NotBlank(message = "Companion last name is required")
    String lastName;

    @NotNull(message = "Relationship type is required")
    Relationship relationship;

    @NotNull(message = "Date of birth is required")
    LocalDate dateOfBirth;
}