package com.trueview.camera;

import com.trueview.config.TrueViewConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Top-level camera controller that composes subsystems and exposes the current visual CameraState.
 */
public class CameraController {
    private final TrueViewConfig config;
    private final CameraState target = new CameraState();
    private final CameraState current = new CameraState();
    private final CameraState previous = new CameraState();

    private final MovementCamera movementCamera;
    private final LandingCamera landingCamera;
    private final FovController fovController;
    private final CameraEffects effects;

    public CameraController(TrueViewConfig cfg) {
        this.config = cfg;
        this.movementCamera = new MovementCamera(cfg);
        this.landingCamera = new LandingCamera(cfg);
        this.fovController = new FovController(cfg);
        this.effects = new CameraEffects(cfg);
    }

    public void clientTick(MinecraftClient client, float dt) {
        if (!config.enabled || !config.cameraEnabled) {
            resetTransientState();
            return;
        }
        PlayerEntity player = client.player;
        if (player == null) return;

        target.reset();
        movementCamera.computeTarget(player, target, dt);
        landingCamera.computeTarget(player, target, dt);
        effects.computeTarget(player, target, dt);
        fovController.computeTarget(player, target, dt);

        float posResp = 12f;
        float rotResp = config.rotationalSmoothing ? 8f : 1000f;
        current.offsetX = Interpolators.expo(current.offsetX, target.offsetX, posResp, dt);
        current.offsetY = Interpolators.expo(current.offsetY, target.offsetY, posResp, dt);
        current.offsetZ = Interpolators.expo(current.offsetZ, target.offsetZ, posResp, dt);
        current.roll = Interpolators.expo(current.roll, target.roll, rotResp, dt);
        current.extraFov = Interpolators.expo(current.extraFov, target.extraFov, config.fovTransitionSpeed, dt);

        previous.setTo(current);
    }

    public CameraState getRenderState(float partialTicks) {
        CameraState s = new CameraState();
        s.offsetX = lerp(previous.offsetX, current.offsetX, partialTicks);
        s.offsetY = lerp(previous.offsetY, current.offsetY, partialTicks);
        s.offsetZ = lerp(previous.offsetZ, current.offsetZ, partialTicks);
        s.roll = lerp(previous.roll, current.roll, partialTicks);
        s.extraFov = lerp(previous.extraFov, current.extraFov, partialTicks);
        return s;
    }

    public void resetTransientState() {
        target.reset();
        current.reset();
        previous.reset();
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }
}
