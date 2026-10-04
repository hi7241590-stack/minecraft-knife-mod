package com.copilot.knifevisuals;

import com.copilot.knifevisuals.KnifeVisualsConfig.KnifeStyle;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.item.Items;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.item.model.ItemModel;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import org.lwjgl.glfw.GLFW;

public class KnifeVisualsClient implements ClientModInitializer {
    private static final String KEY_CATEGORY = "key.category.knifevisuals";
    private static KeyBinding openGuiKey;
    private static KeyBinding inspectKey;

    @Override
    public void onInitializeClient() {
        registerModelPredicates();
        registerKeys();
        registerTickHandler();
    }

    private void registerModelPredicates() {
        ModelPredicateProviderRegistry.register(Items.DIAMOND_SWORD, Identifier.of("knifevisuals", "style"),
                (stack, world, entity, seed) -> {
                    KnifeVisualsConfig config = KnifeVisualsConfig.getInstance();
                    return config.isEnabled() ? config.getStyle().getPredicateValue() : 0;
                });

        ModelPredicateProviderRegistry.register(Items.NETHERITE_SWORD, Identifier.of("knifevisuals", "style"),
                (stack, world, entity, seed) -> {
                    KnifeVisualsConfig config = KnifeVisualsConfig.getInstance();
                    return config.isEnabled() ? config.getStyle().getPredicateValue() : 0;
                });

        ModelPredicateProviderRegistry.register(Items.DIAMOND_SWORD, Identifier.of("knifevisuals", "inspect"),
                (stack, world, entity, seed) -> KnifeVisualsConfig.getInstance().isInspecting() ? 1.0F : 0.0F);

        ModelPredicateProviderRegistry.register(Items.NETHERITE_SWORD, Identifier.of("knifevisuals", "inspect"),
                (stack, world, entity, seed) -> KnifeVisualsConfig.getInstance().isInspecting() ? 1.0F : 0.0F);
    }

    private void registerKeys() {
        openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.knifevisuals.open_gui",
                GLFW.GLFW_KEY_O,
                KEY_CATEGORY
        ));

        inspectKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.knifevisuals.inspect",
                GLFW.GLFW_KEY_G,
                KEY_CATEGORY
        ));
    }

    private void registerTickHandler() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }

            if (openGuiKey.wasPressed()) {
                client.setScreen(new KnifeVisualsConfigScreen(client.currentScreen));
            }

            if (inspectKey.wasPressed()) {
                KnifeVisualsConfig config = KnifeVisualsConfig.getInstance();
                config.setInspecting(!config.isInspecting());
                config.save();
                client.player.sendMessage(Text.translatable(config.isInspecting() ? "text.knifevisuals.inspect.on" : "text.knifevisuals.inspect.off"), true);
            }
        });
    }
}
