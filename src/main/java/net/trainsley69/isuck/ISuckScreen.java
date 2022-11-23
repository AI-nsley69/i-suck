package net.trainsley69.isuck;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.trainsley69.isuck.utils.XRayHelper;

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
        int buttonW = Math.min(this.width / 4, 200);
        int buttonH = 20;
        int buttonOffset = 4;
        int height = this.height / 4;
        int width1 = this.width / 3 - buttonOffset - (buttonW / 2);
        int width2 = this.width - this.width / 3 - buttonOffset - (buttonW / 2);

        // LEFT ROW
        // Autofishing button
        this.addDrawableChild(new ButtonWidget(width1, (height + 1 * 24) - y, buttonW, buttonH, getText("AutoFishing", ISuck.config.AutoFish),
                btn -> {
                    ISuck.config.AutoFish = !ISuck.config.AutoFish;
                    btn.setMessage(getText("AutoFishing", ISuck.config.AutoFish));
                }));
        // Flying button
        this.addDrawableChild(new ButtonWidget(width1, (height + 2 * 24) - y, buttonW, buttonH, getText("Flyhack", ISuck.config.Flying),
                btn -> {
                    ISuck.config.Flying = !ISuck.config.Flying;
                    btn.setMessage(getText("Flyhack", ISuck.config.Flying));
                }));
        // XRay
        this.addDrawableChild(new ButtonWidget(width1, (height + 3 * 24) - y, buttonW, buttonH, getText("XRay", ISuck.config.XRay),
                btn -> {
                    ISuck.config.XRay = !ISuck.config.XRay;
                    btn.setMessage(getText("XRay", ISuck.config.XRay));
                    XRayHelper.changeSetting();
                }));
        this.addDrawableChild(new ButtonWidget(width1, (height + 4 * 24) - y, buttonW, buttonH, getText("NoAbuse", ISuck.config.NoAbuse),
                btn -> {
                    ISuck.config.NoAbuse = !ISuck.config.NoAbuse;
                    btn.setMessage(getText("NoAbuse", ISuck.config.NoAbuse));
                }));
        this.addDrawableChild(new ButtonWidget(width1, (height + 5 * 24) - y, buttonW, buttonH, getText("AutoTool", ISuck.config.AutoTool),
                btn -> {
                    ISuck.config.AutoTool = !ISuck.config.AutoTool;
                    btn.setMessage(getText("AutoTool", ISuck.config.AutoTool));
                }));
        // RIGHT ROW
        // Fullbright
        this.addDrawableChild(new ButtonWidget(width2, (height + 1 * 24) - y, buttonW, buttonH, getText("Fullbright", ISuck.config.Fullbright),
                btn -> {
                    ISuck.config.Fullbright = !ISuck.config.Fullbright;
                    btn.setMessage(getText("Fullbright", ISuck.config.Fullbright));
                }));
        this.addDrawableChild(new ButtonWidget(width2, (height + 2 * 24) - y, buttonW, buttonH, getText("AutoReplant", ISuck.config.AutoReplant),
                btn -> {
                    ISuck.config.AutoReplant = !ISuck.config.AutoReplant;
                    btn.setMessage(getText("AutoReplant", ISuck.config.AutoReplant));
                }));
        this.addDrawableChild(new ButtonWidget(width2, (height + 3 * 24) - y, buttonW, buttonH, getText("NoFog", ISuck.config.NoFog),
                btn -> {
                    ISuck.config.NoFog = !ISuck.config.NoFog;
                    btn.setMessage(getText("NoFog", ISuck.config.NoFog));
                    ISuck.reloadRenderer();
                }));

        this.addDrawableChild(new ButtonWidget(width2, (height + 4 * 24) - y, buttonW, buttonH, getText("FastBreak", ISuck.config.FastBreak),
                btn -> {
                    ISuck.config.FastBreak = !ISuck.config.FastBreak;
                    btn.setMessage(getText("FastBreak", ISuck.config.FastBreak));
                }));
        this.addDrawableChild(new ButtonWidget(width2, (height + 5 * 24) - y, buttonW, buttonH, getText("EntityGlow", ISuck.config.EntityGlow),
                btn -> {
                    ISuck.config.EntityGlow = !ISuck.config.EntityGlow;
                    btn.setMessage(getText("EntityGlow", ISuck.config.EntityGlow));
                }));
        int backButtonW = Math.min((int)(buttonW * 1.75), 300);
        // Back button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - (backButtonW / 2), (height + 6 * 24) - y, backButtonW, 20, ScreenTexts.BACK,
                btn -> {
                    this.client.setScreen(this.parent);
                }));
    }

    private Text getText(String text, Boolean toggled) {
        return Text.literal(text + ": " + (toggled ? "Enabled" : "Disabled"));
    }
}
