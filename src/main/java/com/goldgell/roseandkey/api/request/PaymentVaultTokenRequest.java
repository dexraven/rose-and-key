package com.goldgell.roseandkey.api.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class PaymentVaultTokenRequest {
    @NotBlank(message = "Customer vault token is required")
    private String vaultedCustomerToken;

    @NotBlank(message = "Default payment method token is required")
    private String defaultPaymentMethodToken;
}