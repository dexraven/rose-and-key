package com.goldgell.roseandkey.translation;

import org.junit.jupiter.api.BeforeEach;

public abstract class Test_MarketingPredictionTranslator {

    protected MarketingPredictionTranslator translator;

    @BeforeEach
    void setUp() {
        translator = new MarketingPredictionTranslator();
    }
}