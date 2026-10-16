package com.trueview.mixin;

import com.trueview.TrueViewClient;
import com.trueview.camera.CameraState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.render.FirstPersonRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.MinecraftClient;

@Mixin(FirstPersonRenderer.class)
public abstract class FirstPersonRendererMixin {
    @Inject(method = "renderHand", at = @At("HEAD"))
    private void tv_applyVisualOffsets(MatrixStack matrices, CallbackInfo ci) {
        try {
            if (!MinecraftClient.getInstance().isPaused()) {
                CameraState s = TrueViewClient.getCameraController().getRenderState(MinecraftClient.getInstance().getTickDelta());
                matrices.push();
                matrices.translate(s.offsetX, s.offsetY, s.offsetZ);
                if (s.roll != 0f) {
                    // placeholder: real rotation multiply requires correct matrix util import; mapping adjustments likely needed
                }
            }
        } catch (Throwable t) {
        }
    }

    @Inject(method = "renderHand", at = @At("RETURN"))
    private void tv_restoreVisualOffsets(MatrixStack matrices, CallbackInfo ci) {
        try {
            matrices.pop();
        } catch (Throwable t) {}
    }
}
