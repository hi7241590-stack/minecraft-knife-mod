package com.copilot.knifevisuals;

import com.copilot.knifevisuals.KnifeVisualsConfig.KnifeStyle;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class KnifeVisualsConfigScreen extends Screen {
    private final Screen parent;

    public KnifeVisualsConfigScreen(Screen parent) {
        super(Text.literal("Knife Visuals"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int y = 60;

        int buttonWidth = 160;
        int buttonHeight = 20;

        addDrawableChild(ButtonWidget.builder(
                Text.translatable("text.knifevisuals.style.normal"),
                button -> applyStyle(KnifeStyle.NORMAL)
        ).dimensions(centerX - buttonWidth / 2, y, buttonWidth, buttonHeight).build());

        addDrawableChild(ButtonWidget.builder(
                Text.translatable("text.knifevisuals.style.karambit"),
                button -> applyStyle(KnifeStyle.KARAMBIT)
        ).dimensions(centerX - buttonWidth / 2, y + 30, buttonWidth, buttonHeight).build());

        addDrawableChild(ButtonWidget.builder(
                Text.translatable("text.knifevisuals.style.butterfly"),
                button -> applyStyle(KnifeStyle.BUTTERFLY)
        ).dimensions(centerX - buttonWidth / 2, y + 60, buttonWidth, buttonHeight).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal(KnifeVisualsConfig.getInstance().isEnabled() ? "Disable Mod" : "Enable Mod"),
                button -> {
                    KnifeVisualsConfig config = KnifeVisualsConfig.getInstance();
                    config.setEnabled(!config.isEnabled());
                    config.save();
                    button.setMessage(Text.literal(config.isEnabled() ? "Disable Mod" : "Enable Mod"));
                }
        ).dimensions(centerX - buttonWidth / 2, y + 100, buttonWidth, buttonHeight).build());

        addDrawableChild(ButtonWidget.builder(
                Text.literal("Close"),
                button -> this.close()
        ).dimensions(centerX - buttonWidth / 2, this.height - 40, buttonWidth, buttonHeight).build());
    }

    private void applyStyle(KnifeStyle style) {
        KnifeVisualsConfig config = KnifeVisualsConfig.getInstance();
        config.setStyle(style);
        config.save();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, Text.literal("Knife Visuals"), this.width / 2, 20, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("Style: " + KnifeVisualsConfig.getInstance().getStyle().name()),
                this.width / 2,
                40,
                0xAAAAAA);
    }

    @Override
    public void close() {
        MinecraftClient.getInstance().setScreen(this.parent);
    }
}
