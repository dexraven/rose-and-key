package com.goldgell.roseandkey.translation;

import org.junit.jupiter.api.BeforeEach;

public abstract class Test_SupplierRateTranslator {

    protected SupplierRateTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new SupplierRateTranslator();
    }
}