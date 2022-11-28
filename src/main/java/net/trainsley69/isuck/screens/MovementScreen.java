package net.trainsley69.isuck.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.options.DolphinHackOption;
import net.trainsley69.isuck.options.FlyHackOption;
import net.trainsley69.isuck.options.JumpHackOption;
import net.trainsley69.isuck.options.Option;
import net.trainsley69.isuck.utils.ScreenHelper;

public class MovementScreen extends Screen {

    private final Screen parent;
    private final GameOptions settings;

    public MovementScreen(Screen parent, GameOptions options) {
        super(Text.literal("Movement"));
        this.parent = parent;
        this.settings = options;
    }

    Option[] row1 = {
            new DolphinHackOption("DolphinHack", Option.Type.BUTTON),
            new FlyHackOption("FlyHack", Option.Type.BUTTON),
            new JumpHackOption("JumpHack", Option.Type.SLIDER)
    };

    @Override
    public void removed() {
        ISuck.writeConfig();
    }

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
