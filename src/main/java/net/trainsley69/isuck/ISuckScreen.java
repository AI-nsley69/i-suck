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

    @Override
    public void removed() {
        ISuck.writeConfig();
    }

    protected void init() {
        // Autofishing button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 90, 200, 20, AutoFishing(),
                btn -> {
                    ISuck.config.AutoFish = !ISuck.config.AutoFish;
                    btn.setMessage(AutoFishing());
                }));
        // Flying button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 120, 200, 20, Flying(),
                btn -> {
                    ISuck.config.Flying = !ISuck.config.Flying;
                    btn.setMessage(Flying());
                }));
        // Fullbright
        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 150, 200, 20, Fullbright(),
                btn -> {
                    ISuck.config.Fullbright = !ISuck.config.Fullbright;
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
        return Text.literal(string + (ISuck.config.AutoFish ? "Enabled" : "Disabled"));
    }

    Text Flying() {
        String string = "Flying: ";
        return Text.literal(string + (ISuck.config.Flying ? "Enabled" : "Disabled"));
    }

    Text Fullbright() {
        String string = "Fullbright: ";
        return Text.literal(string + (ISuck.config.Fullbright ? "Enabled" : "Disabled"));
    }
}
