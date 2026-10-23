package com.goldgell.roseandkey.translation;

import org.junit.jupiter.api.BeforeEach;

public abstract class Test_AccountingTranslator {

    protected AccountingTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new AccountingTranslator();
    }
}