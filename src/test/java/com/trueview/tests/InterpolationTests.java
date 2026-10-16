package com.trueview.tests;

import com.trueview.camera.Interpolators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class InterpolationTests {
    @Test
    public void expoMovesTowardsTarget() {
        float cur = 0f;
        float target = 10f;
        float resp = 5f; // per-second
        float dt = 0.1f;
        float next = Interpolators.expo(cur, target, resp, dt);
        assertTrue(next > cur);
        for (int i = 0; i < 1000; i++) {
            cur = Interpolators.expo(cur, target, resp, dt);
        }
        assertTrue(Math.abs(cur - target) < 0.01f);
    }
}
