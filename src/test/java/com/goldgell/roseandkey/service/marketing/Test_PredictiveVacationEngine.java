package com.goldgell.roseandkey.service.marketing;

import org.junit.jupiter.api.BeforeEach;

public abstract class Test_PredictiveVacationEngine {

    protected PredictiveVacationEngine engine;

    @BeforeEach
    void setUp() {
        engine = new PredictiveVacationEngine();
    }
}