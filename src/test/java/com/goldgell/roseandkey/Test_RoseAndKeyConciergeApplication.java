package com.goldgell.roseandkey;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;

@SpringBootTest
class Test_RoseAndKeyConciergeApplication {

    @Test
    @DisplayName("Application Context Loads without circular dependencies or missing beans")
    void contextLoads() {
        assertThatNoException().isThrownBy(() -> {});
    }
}