package com.goldgell.roseandkey.service.booking;

import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.domain.party.ClientProfile;
import com.goldgell.roseandkey.repository.booking.BookingRecordRepository;
import com.goldgell.roseandkey.repository.intake.TripIntakeRepository;
import com.goldgell.roseandkey.supplier.gateway.SupplierGateway;
import com.goldgell.roseandkey.translation.BookingRecordTranslator;
import com.goldgell.roseandkey.validation.SupplierCommandValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public abstract class Test_BookingRecordService {

    @Mock
    protected TripIntakeRepository tripIntakeRepository;

    @Mock
    protected BookingRecordRepository bookingRecordRepository;

    @Mock
    protected SupplierGateway supplierGateway;

    @Mock
    protected SupplierCommandValidator supplierCommandValidator;

    @Mock
    protected BookingRecordTranslator bookingRecordTranslator;

    @InjectMocks
    protected BookingRecordService bookingRecordService;

    protected TripIntake createMockIntake(UUID intakeId) {
        ClientProfile client = ClientProfile.builder()
                .firstName("Belle")
                .lastName("French")
                .email("belle@castle.org")
                .defaultPaymentMethodToken("pm_default_vault")
                .build();

        return TripIntake.builder()
                .id(intakeId)
                .client(client)
                .status(TripIntake.TripStatus.INTAKE_SUBMITTED)
                .build();
    }
}