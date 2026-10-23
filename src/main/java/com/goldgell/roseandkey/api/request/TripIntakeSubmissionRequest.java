package com.goldgell.roseandkey.api.request;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.time.LocalDate;
import java.util.List;

@Value
@Builder
@Jacksonized
public class TripIntakeSubmissionRequest {

    @NotNull(message = "Lead traveler details are required")
    @Valid
    LeadTravelerRequest leadTraveler;

    @NotNull(message = "Destination target supplier is required")
    SupplierCategory destinationTarget;

    @NotNull(message = "Earliest departure date is required")
    LocalDate earliestDeparture;

    @NotNull(message = "Latest return date is required")
    LocalDate latestReturn;

    @Min(value = 0, message = "Target budget cannot be negative")
    long targetBudgetCents;

    @Min(value = 1, message = "Party headcount must be at least 1")
    int partyHeadcount;

    String specialNotes;

    List<@Valid CompanionInputRequest> companions;
}