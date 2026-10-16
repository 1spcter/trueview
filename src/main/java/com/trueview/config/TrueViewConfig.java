package com.trueview.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * Config model and persistence.
 */
public class TrueViewConfig {
    private static final ObjectMapper MAPPER = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    private static TrueViewConfig INSTANCE;
    private static final Path CONFIG_PATH = Path.of("config").resolve("trueview.json");

    // Camera
    public boolean enabled = true;
    public boolean cameraEnabled = true;
    public boolean rotationalSmoothing = false;

    // Movement
    public float headBobIntensity = 0.5f; // 0..1
    public float sprintMultiplier = 1.2f;
    public float sneakMultiplier = 0.2f;
    public float jumpIntensity = 0.7f;
    public float landingIntensity = 0.6f;
    public float cameraRollIntensity = 0.05f;
    public float handMovementIntensity = 0.5f;

    // FOV
    public boolean sprintFovEnabled = true;
    public float additionalSprintFov = 5.0f; // degrees
    public float fovTransitionSpeed = 6.0f; // seconds^-1 responsiveness

    // Environment
    public boolean envAdjustments = true;

    // Accessibility
    public boolean reduceMotion = false;

    // Versioning / preset
    public String preset = "vanilla";

    public static TrueViewConfig get() {
        if (INSTANCE == null) INSTANCE = new TrueViewConfig();
        return INSTANCE;
    }

    public static void loadOrCreate() {
        try {
            File f = CONFIG_PATH.toFile();
            if (f.exists()) {
                INSTANCE = MAPPER.readValue(f, TrueViewConfig.class);
            } else {
                INSTANCE = new TrueViewConfig();
                save();
            }
        } catch (IOException e) {
            System.err.println("TrueView: failed to load config, using defaults: " + e.getMessage());
            INSTANCE = new TrueViewConfig();
        }
    }

    public static void save() {
        try {
            File f = CONFIG_PATH.toFile();
            f.getParentFile().mkdirs();
            MAPPER.writeValue(f, get());
        } catch (IOException e) {
            System.err.println("TrueView: failed to save config: " + e.getMessage());
        }
    }

    public void validateAndClamp() {
        headBobIntensity = clamp(headBobIntensity, 0f, 2f);
        sprintMultiplier = clamp(sprintMultiplier, 0f, 3f);
        sneakMultiplier = clamp(sneakMultiplier, 0f, 1f);
        jumpIntensity = clamp(jumpIntensity, 0f, 2f);
        landingIntensity = clamp(landingIntensity, 0f, 3f);
        cameraRollIntensity = clamp(cameraRollIntensity, 0f, 1f);
        handMovementIntensity = clamp(handMovementIntensity, 0f, 1f);
        additionalSprintFov = clamp(additionalSprintFov, 0f, 30f);
        fovTransitionSpeed = clamp(fovTransitionSpeed, 0.1f, 30f);
    }

    private static float clamp(float v, float a, float b) {
        return Math.max(a, Math.min(b, v));
    }
}
