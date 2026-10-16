package com.trueview.camera;

import com.trueview.config.TrueViewConfig;
import net.minecraft.entity.player.PlayerEntity;

/**
 * Optional roll and other small effects.
 */
public class CameraEffects {
    private final TrueViewConfig cfg;

    public CameraEffects(TrueViewConfig cfg) {
        this.cfg = cfg;
    }

    public void computeTarget(PlayerEntity player, CameraState out, float dt) {
        if (!cfg.cameraEnabled || cfg.cameraRollIntensity <= 0f) return;
        float moveX = (float) player.getVelocity().x;
        float lateral = moveX;
        float roll = -lateral * cfg.cameraRollIntensity * 10f;
        if (cfg.reduceMotion) roll *= 0.2f;
        out.roll += roll;
    }
}
