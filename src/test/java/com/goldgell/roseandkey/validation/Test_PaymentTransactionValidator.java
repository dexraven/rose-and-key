package com.goldgell.roseandkey.validation;

import org.junit.jupiter.api.BeforeEach;

public abstract class Test_PaymentTransactionValidator {

    protected PaymentTransactionValidator validator;

    @BeforeEach
    void setUp() {
        validator = new PaymentTransactionValidator();
    }
}