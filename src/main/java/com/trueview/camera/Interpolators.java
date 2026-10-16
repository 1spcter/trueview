package com.trueview.camera;

/**
 * Utility interpolation functions.
 */
public class Interpolators {
    public static float expo(float current, float target, float responsivenessPerSecond, float dt) {
        if (responsivenessPerSecond <= 0f) return target;
        float alpha = 1f - (float)Math.exp(-responsivenessPerSecond * dt);
        return current + (target - current) * alpha;
    }
}
