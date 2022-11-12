package net.trainsley69.isuck;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ISuckScreen extends Screen {
    private Screen parent;
    private GameOptions settings;

    public ISuckScreen(Screen parent, GameOptions settings) {
        super(Text.literal("I Suck"));
        this.parent = parent;
        this.settings = settings;
    }

    protected void init() {
        // Autofishing button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 90, 200, 20, AutoFishing(),
                btn -> {
                    ISuck.Config.setAutoFish(!ISuck.Config.isAutoFish());
                    btn.setMessage(AutoFishing());
                }));
        // Flying button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 120, 200, 20, Flying(),
                btn -> {
                    ISuck.Config.setFlying(!ISuck.Config.isFlying());
                    btn.setMessage(Flying());
                }));
        // Fullbright
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 150, 200, 20, Fullbright(),
                btn -> {
                    ISuck.Config.setFullbright(!ISuck.Config.isFullbright());
                    btn.setMessage(Fullbright());
                }));
        // Back button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 180, 200, 20, ScreenTexts.BACK,
                btn -> {
                    this.client.setScreen(this.parent);
                }));
    }

    Text AutoFishing() {
        String string = "Auto Fishing: ";
        return Text.literal(string + (ISuck.Config.isAutoFish() ? "Enabled" : "Disabled"));
    }

    Text Flying() {
        String string = "Flying: ";
        return Text.literal(string + (ISuck.Config.isFlying() ? "Enabled" : "Disabled"));
    }

    Text Fullbright() {
        String string = "Fullbright: ";
        return Text.literal(string + (ISuck.Config.isFullbright() ? "Enabled" : "Disabled"));
    }
}
