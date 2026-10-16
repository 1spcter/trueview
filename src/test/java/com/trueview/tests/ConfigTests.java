package com.trueview.tests;

import com.trueview.config.TrueViewConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConfigTests {
    @Test
    public void testDefaultValidation() {
        TrueViewConfig cfg = TrueViewConfig.get();
        cfg.headBobIntensity = -5f;
        cfg.additionalSprintFov = 1000f;
        cfg.validateAndClamp();
        assertTrue(cfg.headBobIntensity >= 0f && cfg.headBobIntensity <= 2f);
        assertTrue(cfg.additionalSprintFov <= 30f);
    }
}
