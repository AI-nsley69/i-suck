package net.trainsley69.isuck;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.WorldRenderer;
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
        int y = 5 / 2;
        int buttonW = 150;
        int buttonH = 20;
        int buttonOffset = 4;
        int height = this.height / 4;
        int width1 = this.width / 3 - buttonOffset - (buttonW / 2);
        int width2 = this.width - this.width / 3 + buttonOffset - (buttonW / 2);

        // LEFT ROW
        // Autofishing button
        this.addDrawableChild(new ButtonWidget(width1, (height + 1 * 24) - y, buttonW, buttonH, AutoFishing(),
                btn -> {
                    ISuck.config.AutoFish = !ISuck.config.AutoFish;
                    btn.setMessage(AutoFishing());
                }));
        // Flying button
        this.addDrawableChild(new ButtonWidget(width1, (height + 2 * 24) - y, buttonW, buttonH, Flying(),
                btn -> {
                    ISuck.config.Flying = !ISuck.config.Flying;
                    btn.setMessage(Flying());
                }));
        // XRay
        this.addDrawableChild(new ButtonWidget(width1, (height + 3 * 24) - y, buttonW, buttonH, XRay(),
                btn -> {
                    ISuck.config.XRay = !ISuck.config.XRay;
                    btn.setMessage(XRay());
                    MinecraftClient client = MinecraftClient.getInstance();
                    client.worldRenderer.reload();
                }));

        // RIGHT ROW
        // Fullbright
        this.addDrawableChild(new ButtonWidget(width2, (height + 1 * 24) - y, buttonW, buttonH, Fullbright(),
                btn -> {
                    ISuck.config.Fullbright = !ISuck.config.Fullbright;
                    btn.setMessage(Fullbright());
                }));
        this.addDrawableChild(new ButtonWidget(width2, (height + 2 * 24) - y, buttonW, buttonH, AutoReplant(),
                btn -> {
                    ISuck.config.AutoReplant = !ISuck.config.AutoReplant;
                    btn.setMessage(AutoReplant());
                }));

        // Back button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - ((int) (buttonW * 1.75) / 2), (height + 4 * 24) - y, (int) (buttonW * 1.75), 20, ScreenTexts.BACK,
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

    Text AutoReplant() {
        String string = "AutoReplant: ";
        return Text.literal(string + (ISuck.config.AutoReplant ? "Enabled" : "Disabled"));
    }

    Text XRay() {
        String string = "XRay: ";
        return Text.literal(string + (ISuck.config.XRay ? "Enabled" : "Disabled"));
    }
}
