package com.goldgell.roseandkey.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goldgell.roseandkey.api.advice.GlobalExceptionHandler;
import com.goldgell.roseandkey.service.party.ClientProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public abstract class Test_ClientProfileController {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @Mock
    protected ClientProfileService clientProfileService;

    @InjectMocks
    protected ClientProfileController clientProfileController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(clientProfileController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
}