package com.copilot.knifevisuals;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class KnifeVisualsConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static KnifeVisualsConfig INSTANCE;

    private boolean enabled = true;
    private KnifeStyle style = KnifeStyle.NORMAL;
    private boolean inspecting = false;

    public static KnifeVisualsConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new KnifeVisualsConfig();
        }
        return INSTANCE;
    }

    public static void load() {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("knifevisuals.json");
        if (!Files.exists(configFile)) {
            INSTANCE = new KnifeVisualsConfig();
            INSTANCE.save();
            return;
        }

        try {
            String json = Files.readString(configFile);
            INSTANCE = GSON.fromJson(json, KnifeVisualsConfig.class);
            if (INSTANCE == null) {
                INSTANCE = new KnifeVisualsConfig();
            }
        } catch (IOException e) {
            INSTANCE = new KnifeVisualsConfig();
        }
    }

    public void save() {
        Path configFile = FabricLoader.getInstance().getConfigDir().resolve("knifevisuals.json");
        try {
            Files.createDirectories(configFile.getParent());
            Files.writeString(configFile, GSON.toJson(this));
        } catch (IOException e) {
            throw new RuntimeException("Failed to save knifevisuals config", e);
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public KnifeStyle getStyle() {
        return style;
    }

    public void setStyle(KnifeStyle style) {
        this.style = style;
    }

    public boolean isInspecting() {
        return inspecting;
    }

    public void setInspecting(boolean inspecting) {
        this.inspecting = inspecting;
    }

    public enum KnifeStyle {
        NORMAL(0),
        KARAMBIT(1),
        BUTTERFLY(2);

        private final int predicateValue;

        KnifeStyle(int predicateValue) {
            this.predicateValue = predicateValue;
        }

        public int getPredicateValue() {
            return predicateValue;
        }

        public static KnifeStyle fromPredicateValue(int value) {
            for (KnifeStyle style : values()) {
                if (style.predicateValue == value) {
                    return style;
                }
            }
            return NORMAL;
        }
    }
}
