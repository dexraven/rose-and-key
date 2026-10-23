package com.goldgell.roseandkey.client.payment;

import com.goldgell.roseandkey.validation.PaymentTransactionValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
public abstract class Test_PaymentGatewayClient {

    @Mock
    protected RestClient restClient;

    @Mock
    protected PaymentTransactionValidator validator;

    @InjectMocks
    protected StripePaymentGatewayClient paymentGatewayClient;
}