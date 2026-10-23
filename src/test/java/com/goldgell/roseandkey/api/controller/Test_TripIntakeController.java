package com.goldgell.roseandkey.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goldgell.roseandkey.api.advice.GlobalExceptionHandler;
import com.goldgell.roseandkey.api.request.LeadTravelerRequest;
import com.goldgell.roseandkey.api.request.TripIntakeSubmissionRequest;
import com.goldgell.roseandkey.domain.intake.TripIntake;
import com.goldgell.roseandkey.service.intake.TripIntakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public abstract class Test_TripIntakeController {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @Mock
    protected TripIntakeService tripIntakeService;

    @InjectMocks
    protected TripIntakeController tripIntakeController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(tripIntakeController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    protected TripIntakeSubmissionRequest createValidPayload() {
        LocalDate dep = LocalDate.now().plusMonths(2);
        return TripIntakeSubmissionRequest.builder()
                .leadTraveler(LeadTravelerRequest.builder()
                        .firstName("Belle")
                        .lastName("French")
                        .email("belle@villagelibrary.org")
                        .phoneE164("+12105550199")
                        .isGuest(true)
                        .build())
                .destinationTarget(TripIntake.SupplierCategory.DISNEY)
                .earliestDeparture(dep)
                .latestReturn(dep.plusDays(7))
                .targetBudgetCents(600000L)
                .partyHeadcount(1)
                .build();
    }
}