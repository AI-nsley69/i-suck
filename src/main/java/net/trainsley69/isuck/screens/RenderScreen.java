package net.trainsley69.isuck.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.options.*;
import net.trainsley69.isuck.utils.ScreenHelper;

public class RenderScreen extends Screen {
    private final Screen parent;

    private final GameOptions settings;

    public RenderScreen(Screen parent, GameOptions options) {
        super(Text.literal("Render"));
        this.parent = parent;
        this.settings = options;
    }

    @Override
    public void removed() {
        ISuck.writeConfig();
    }

    Option[] row1 = {
            new NoFogOption("NoFog", Option.Type.BUTTON),
            new FullbrightOption("Fullbright", Option.Type.BUTTON),
            new XRayOption("XRay", Option.Type.BUTTON),
            new EntityGlowOption("EntityGlow", Option.Type.BUTTON),
    };

    protected void init() {
        Object[] buttons = ScreenHelper.singleRow(row1, this, this.parent, this.client);
        for (var b : buttons) {
            Option.Type type = ScreenHelper.getWidgetType(b);
            switch (type) {
                case BUTTON -> this.addDrawableChild((ButtonWidget)b);
                case SLIDER -> this.addDrawableChild((SliderWidget)b);
            }
        }
    }
}
