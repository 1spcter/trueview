package com.trueview.camera;

import com.trueview.config.TrueViewConfig;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Landing response.
 */
public class LandingCamera {
    private final TrueViewConfig cfg;
    private float impactTimer = 0f;
    private float impactAmount = 0f;

    public LandingCamera(TrueViewConfig cfg) {
        this.cfg = cfg;
    }

    public void computeTarget(PlayerEntity player, CameraState out, float dt) {
        if (!cfg.cameraEnabled || cfg.landingIntensity <= 0f) return;
        if (player.isOnGround() && player.getVelocity().y == 0.0 && player.fallDistance > 0.5f) {
            float severity = Math.min(1.0f, player.fallDistance / 10.0f);
            impactAmount = severity * cfg.landingIntensity * 0.15f;
            impactTimer = 0.35f;
            player.fallDistance = 0.0f;
        }

        if (impactTimer > 0f) {
            float t = 1f - (impactTimer / 0.35f);
            float dy = impactAmount * (1.0f - t);
            out.offsetY -= dy;
            impactTimer -= dt;
        }
    }
}
