package com.trueview;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import com.trueview.config.TrueViewConfig;
import com.trueview.camera.CameraController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the TrueView client-side mod.
 */
public class TrueViewClient implements ClientModInitializer {
    public static final String MODID = "trueview";
    public static final Logger LOGGER = LoggerFactory.getLogger("TrueView");

    private static CameraController cameraController;

    @Override
    public void onInitializeClient() {
        LOGGER.info("Initializing TrueView client");
        TrueViewConfig.loadOrCreate(); // loads config from disk
        cameraController = new CameraController(TrueViewConfig.get());

        // Tick event: update controller each tick
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) {
                cameraController.resetTransientState();
                return;
            }
            cameraController.clientTick(client, 1.0f / 60f);
        });

        LOGGER.info("TrueView initialized");
    }

    public static CameraController getCameraController() {
        return cameraController;
    }
}
