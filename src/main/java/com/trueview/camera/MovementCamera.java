package com.trueview.camera;

import com.trueview.config.TrueViewConfig;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Phase-based head bob system.
 */
public class MovementCamera {
    private final TrueViewConfig cfg;
    private double phase = 0.0;

    public MovementCamera(TrueViewConfig cfg) {
        this.cfg = cfg;
    }

    public void computeTarget(PlayerEntity player, CameraState out, float dt) {
        if (!cfg.cameraEnabled || cfg.headBobIntensity <= 0f) return;
        double speed = Math.sqrt(player.getVelocity().x * player.getVelocity().x + player.getVelocity().z * player.getVelocity().z);
        boolean moving = speed > 1e-4 && !player.isOnGround() == false;
        boolean sprinting = player.isSprinting();
        boolean sneaking = player.isInSneakingPose();

        double baseFreq = 6.0d;
        double amplitude = 0.02d * cfg.headBobIntensity;
        if (sprinting) {
            baseFreq *= cfg.sprintMultiplier;
            amplitude *= cfg.sprintMultiplier;
        }
        if (sneaking) {
            amplitude *= cfg.sneakMultiplier;
        }

        if (moving) {
            phase += baseFreq * dt;
        } else {
            phase *= Math.pow(0.9, dt * 60.0);
        }

        double vx = Math.sin(phase) * amplitude;
        double vy = Math.abs(Math.cos(phase)) * amplitude;

        out.offsetX += (float) (vx * cfg.headBobIntensity * cfg.handMovementIntensity);
        out.offsetY += (float) (vy * cfg.headBobIntensity);
        out.offsetZ += (float) (-Math.cos(phase) * amplitude * 0.25 * cfg.headBobIntensity);
    }
}
