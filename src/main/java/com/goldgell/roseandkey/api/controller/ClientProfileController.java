package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.CompanionUpdateRequest;
import com.goldgell.roseandkey.api.request.PaymentVaultTokenRequest;
import com.goldgell.roseandkey.api.response.ClientProfileResponse;
import com.goldgell.roseandkey.service.party.ClientProfileService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientProfileController {

    private final ClientProfileService clientProfileService;

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientProfileResponse> getProfile(@PathVariable UUID clientId) {
        return ResponseEntity.ok(clientProfileService.getProfile(clientId));
    }

    @PostMapping("/{clientId}/companions")
    public ResponseEntity<ClientProfileResponse> addCompanion(
            @PathVariable UUID clientId,
            @Valid @RequestBody CompanionUpdateRequest request) {
        return ResponseEntity.ok(clientProfileService.addCompanion(clientId, request));
    }

    @PutMapping("/{clientId}/payment-vault")
    public ResponseEntity<Void> updatePaymentVault(
            @PathVariable UUID clientId,
            @Valid @RequestBody PaymentVaultTokenRequest request) {
        clientProfileService.updateVaultedPaymentTokens(clientId, request);
        return ResponseEntity.noContent().build();
    }
}