package net.trainsley69.isuck;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.trainsley69.isuck.options.*;
import net.trainsley69.isuck.screens.AutoScreen;
import net.trainsley69.isuck.screens.ExtraScreen;
import net.trainsley69.isuck.screens.MovementScreen;
import net.trainsley69.isuck.screens.RenderScreen;

public class ISuckScreen extends Screen {
    private final Screen parent;
    private final GameOptions settings;

    Option[] row1 = {
            new AutoFishOption("AutoFish", Option.Type.BUTTON),
            new FlyHackOption("FlyHack", Option.Type.BUTTON),
            new XRayOption("XRay", Option.Type.BUTTON),
            new NoAbuseOption("NoAbuse", Option.Type.BUTTON),
            new JumpHackOption("JumpHack", Option.Type.SLIDER),
            new DolphinHackOption("DolphinHack", Option.Type.BUTTON)
    };

    Option[] row2 = {
            new FullbrightOption("Fullbright", Option.Type.BUTTON),
            new AutoReplantOption("AutoReplant", Option.Type.BUTTON),
            new NoFogOption("NoFog", Option.Type.BUTTON),
            new FastBreakOption("FastBreak", Option.Type.BUTTON),
            new EntityGlowOption("EntityGlow", Option.Type.BUTTON)
    };

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
        int length = 4;
        int y = length / 2;
        int buttonW = Math.min(this.width / 4, 240);
        int buttonH = 20;
        int height = this.height / 5;
        int width1 = this.width / 2 - (buttonW / 2);

        this.addDrawableChild(new ButtonWidget(width1, height + (1 * 24) - y, buttonW, buttonH, Text.literal("Movement"),
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(new MovementScreen(this, settings));
                }
        ));

        this.addDrawableChild(new ButtonWidget(width1, height + (2 * 24) - y, buttonW, buttonH, Text.literal("Render"),
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(new RenderScreen(this, settings));
                }
        ));

        this.addDrawableChild(new ButtonWidget(width1, height + (3 * 24) - y, buttonW, buttonH, Text.literal("AutoHacks"),
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(new AutoScreen(this, settings));
                }
        ));

        this.addDrawableChild(new ButtonWidget(width1, height + (4 * 24) - y, buttonW, buttonH, Text.literal("Extra"),
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(new ExtraScreen(this, settings));
                }
        ));

        int backButtonW = Math.min((int)(buttonW * 1.25), 300);
        // Back button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - (backButtonW / 2), (height + (length + 1) * 24) - y, backButtonW, buttonH, ScreenTexts.BACK,
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(this.parent);
                }));
    }
}
