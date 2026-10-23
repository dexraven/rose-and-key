package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.api.response.TripIntakeResponse;
import com.goldgell.roseandkey.service.intake.TripIntakeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/intakes")
@RequiredArgsConstructor
public class TripIntakeController {

    private final TripIntakeService tripIntakeService;

    @PostMapping
    public ResponseEntity<TripIntakeResponse> submitIntake(
            @Valid @RequestBody TripIntakeSubmissionRequest request,
            @RequestHeader(name = "X-Agent-ID", defaultValue = "agent-kelley-default") String agentId) {
        TripIntakeResponse response = tripIntakeService.submitIntake(request, agentId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/clients/{clientId}/promote")
    public ResponseEntity<Void> promoteGuestToRegistered(@PathVariable UUID clientId) {
        tripIntakeService.promoteGuestToRegistered(clientId);
        return ResponseEntity.noContent().build();
    }
}