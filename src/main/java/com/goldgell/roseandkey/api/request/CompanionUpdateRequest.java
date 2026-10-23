package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.party.Companion.Relationship;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CompanionUpdateRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Relationship is required")
    private Relationship relationship;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;
}