package com.goldgell.roseandkey.supplier.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class LockReservationCommand {
    private String packageId;
    private String intakeId;
    private String leadTravelerFirstName;
    private String leadTravelerLastName;
    private String leadTravelerEmail;
    private String paymentVaultToken;
    private long authorizedAmountCents;
}