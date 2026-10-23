package com.goldgell.roseandkey.api.controller;

import com.goldgell.roseandkey.api.request.PaymentVaultTokenRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class Test_ClientProfileController_updatePaymentVault extends Test_ClientProfileController {

    @Test
    @DisplayName("Happy Path: PUT /api/v1/clients/{clientId}/payment-vault returns 204 No Content on token vaulting")
    void shouldReturn204OnSuccessfulVault() throws Exception {
        UUID clientId = UUID.randomUUID();
        PaymentVaultTokenRequest request = PaymentVaultTokenRequest.builder()
                .vaultedCustomerToken("cus_vault_tok_123")
                .defaultPaymentMethodToken("pm_card_tok_456")
                .build();

        doNothing().when(clientProfileService).updateVaultedPaymentTokens(eq(clientId), any(PaymentVaultTokenRequest.class));

        mockMvc.perform(put("/api/v1/clients/{clientId}/payment-vault", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Negative Scenario: PUT /api/v1/clients/{clientId}/payment-vault returns 400 Bad Request on blank tokens")
    void shouldReturn400OnBlankTokens() throws Exception {
        UUID clientId = UUID.randomUUID();
        PaymentVaultTokenRequest badRequest = PaymentVaultTokenRequest.builder()
                .vaultedCustomerToken("")
                .defaultPaymentMethodToken("")
                .build();

        mockMvc.perform(put("/api/v1/clients/{clientId}/payment-vault", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Validation Failed"));
    }

    @Test
    @DisplayName("Negative Scenario: PUT /api/v1/clients/{clientId}/payment-vault returns 400 Bad Request when client missing")
    void shouldReturn400WhenClientNotFound() throws Exception {
        UUID clientId = UUID.randomUUID();
        PaymentVaultTokenRequest request = PaymentVaultTokenRequest.builder()
                .vaultedCustomerToken("cus_tok_valid")
                .defaultPaymentMethodToken("pm_tok_valid")
                .build();

        doThrow(new IllegalArgumentException("Client profile not found: " + clientId))
                .when(clientProfileService).updateVaultedPaymentTokens(eq(clientId), any(PaymentVaultTokenRequest.class));

        mockMvc.perform(put("/api/v1/clients/{clientId}/payment-vault", clientId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }
}