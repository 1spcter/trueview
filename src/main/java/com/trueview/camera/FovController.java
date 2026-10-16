package com.trueview.camera;

import com.trueview.config.TrueViewConfig;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Smooth sprint FOV controller.
 */
public class FovController {
    private final TrueViewConfig cfg;
    private float targetFov = 0f;

    public FovController(TrueViewConfig cfg) {
        this.cfg = cfg;
    }

    public void computeTarget(PlayerEntity player, CameraState out, float dt) {
        if (!cfg.sprintFovEnabled) {
            targetFov = 0f;
            return;
        }
        boolean sprinting = player.isSprinting();
        float desired = sprinting ? cfg.additionalSprintFov : 0f;
        targetFov = desired;
        out.extraFov += targetFov;
    }
}
