package com.goldgell.roseandkey.api.response;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.domain.intake.TripIntake.TripStatus;
import com.goldgell.roseandkey.domain.party.ClientProfile.AccountType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;
import java.util.UUID;

@Value
@Builder
public class TripIntakeResponse {
    UUID intakeId;
    UUID clientId;
    AccountType accountType;
    String leadTravelerName;
    String leadTravelerEmail;
    SupplierCategory destinationTarget;
    TripStatus status;
    LocalDate earliestDeparture;
    LocalDate latestReturn;
    long targetBudgetCents;
    int partyHeadcount;
}