package com.goldgell.roseandkey.validation;

import org.junit.jupiter.api.BeforeEach;

public abstract class Test_AccountingValidator {

    protected AccountingValidator validator;

    @BeforeEach
    void setUp() {
        validator = new AccountingValidator();
    }
}