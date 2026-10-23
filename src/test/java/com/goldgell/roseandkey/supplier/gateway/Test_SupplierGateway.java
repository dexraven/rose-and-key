package com.goldgell.roseandkey.supplier.gateway;

import com.goldgell.roseandkey.domain.intake.TripIntake.SupplierCategory;
import com.goldgell.roseandkey.supplier.model.AvailabilityQuery;
import com.goldgell.roseandkey.supplier.model.LockReservationCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public abstract class Test_SupplierGateway {

    @Mock
    protected SupplierAdapter disneyAdapter;

    @Mock
    protected SupplierAdapter marriottAdapter;

    protected SupplierGateway supplierGateway;

    @BeforeEach
    void setUp() {
        lenient().when(disneyAdapter.getSupportedCategory()).thenReturn(SupplierCategory.DISNEY);
        lenient().when(marriottAdapter.getSupportedCategory()).thenReturn(SupplierCategory.MARRIOTT);
        supplierGateway = new SupplierGateway(List.of(disneyAdapter, marriottAdapter));
    }

    protected AvailabilityQuery createQuery(SupplierCategory category) {
        return AvailabilityQuery.builder()
                .category(category)
                .checkInDate(LocalDate.of(2026, 12, 19))
                .checkOutDate(LocalDate.of(2026, 12, 27))
                .adultCount(2)
                .destinationCode("WDW")
                .build();
    }

    protected LockReservationCommand createCommand() {
        return LockReservationCommand.builder()
                .intakeId("intake-123")
                .leadTravelerFirstName("Belle")
                .leadTravelerLastName("French")
                .leadTravelerEmail("belle@castle.org")
                .paymentVaultToken("pm_token_safe")
                .authorizedAmountCents(100000L)
                .build();
    }
}