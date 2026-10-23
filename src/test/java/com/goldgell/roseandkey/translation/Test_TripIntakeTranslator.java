package com.goldgell.roseandkey.translation;

import org.junit.jupiter.api.BeforeEach;

public abstract class Test_TripIntakeTranslator {

    protected TripIntakeTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new TripIntakeTranslator();
    }
}