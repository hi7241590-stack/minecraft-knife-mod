package com.copilot.knifevisuals;

import net.fabricmc.api.ModInitializer;

public class KnifeVisualsMod implements ModInitializer {
    public static final String MOD_ID = "knifevisuals";

    @Override
    public void onInitialize() {
        KnifeVisualsConfig.load();
    }
}
