package com.trueview.mixin;

import com.trueview.TrueViewClient;
import com.trueview.camera.CameraState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "updateCamera", at = @At("HEAD"))
    private void tv_applyFov(CallbackInfo ci) {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.player == null) return;
            CameraState s = TrueViewClient.getCameraController().getRenderState(client.getTickDelta());
            if (s.extraFov != 0f) {
                float base = client.options.getFov().getValue();
                client.options.getFov().setValue(base + s.extraFov);
            }
        } catch (Throwable t) {
        }
    }
}
