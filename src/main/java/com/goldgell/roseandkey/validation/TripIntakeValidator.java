package com.goldgell.roseandkey.validation;

import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TripIntakeValidator {

    public void validateSubmission(TripIntakeSubmissionRequest request) {
        if (request.getLatestReturn().isBefore(request.getEarliestDeparture())) {
            throw new IllegalArgumentException("Latest return date cannot precede earliest departure date.");
        }

        if (request.getEarliestDeparture().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Earliest departure date cannot be in the past.");
        }

        int registeredTravelers = 1 + (request.getCompanions() != null ? request.getCompanions().size() : 0);
        if (request.getPartyHeadcount() < registeredTravelers) {
            throw new IllegalArgumentException(
                    String.format("Party headcount (%d) cannot be smaller than registered travelers list (%d).",
                            request.getPartyHeadcount(), registeredTravelers)
            );
        }
    }
}