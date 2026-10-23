package com.goldgell.roseandkey.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.goldgell.roseandkey.api.advice.GlobalExceptionHandler;
import com.goldgell.roseandkey.service.supplier.SupplierRateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public abstract class Test_SupplierGatewayController {

    protected MockMvc mockMvc;
    protected ObjectMapper objectMapper;

    @Mock
    protected SupplierRateService supplierRateService;

    @InjectMocks
    protected SupplierGatewayController supplierGatewayController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(supplierGatewayController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }
}