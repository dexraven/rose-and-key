package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.party.Companion.Relationship;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class CompanionResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private Relationship relationship;
    private LocalDate dateOfBirth;
    private boolean isMinor;
}