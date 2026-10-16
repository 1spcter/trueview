package com.trueview.ui;

import com.trueview.config.TrueViewConfig;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class TrueViewConfigScreen extends Screen {
    private final Screen parent;
    public TrueViewConfigScreen(Screen parent) {
        super(Text.of("TrueView Settings"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void onClose() {
        TrueViewConfig.save();
        client.setScreen(parent);
    }
}
