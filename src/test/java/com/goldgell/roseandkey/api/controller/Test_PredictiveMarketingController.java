package com.goldgell.roseandkey.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goldgell.roseandkey.api.advice.GlobalExceptionHandler;
import com.goldgell.roseandkey.service.marketing.PredictiveVacationEngine;
import com.goldgell.roseandkey.translation.MarketingPredictionTranslator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public abstract class Test_PredictiveMarketingController {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @Mock
    protected PredictiveVacationEngine predictiveVacationEngine;

    @Mock
    protected MarketingPredictionTranslator marketingPredictionTranslator;

    @InjectMocks
    protected PredictiveMarketingController controller;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
        objectMapper = new ObjectMapper();
    }
}