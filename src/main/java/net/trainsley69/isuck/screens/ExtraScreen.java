package net.trainsley69.isuck.screens;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.options.FastBreakOption;
import net.trainsley69.isuck.options.NoAbuseOption;
import net.trainsley69.isuck.options.Option;
import net.trainsley69.isuck.utils.ScreenHelper;

public class ExtraScreen extends Screen {
    private final Screen parent;
    private final GameOptions settings;

    public ExtraScreen(Screen parent, GameOptions options) {
        super(Text.literal("Extras"));
        this.parent = parent;
        this.settings = options;
    }

    @Override
    public void removed() {
        ISuck.writeConfig();
    }

    Option[] row1 = {
            new FastBreakOption("FastBreak", Option.Type.BUTTON),
            new NoAbuseOption("NoAbuse", Option.Type.BUTTON)
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
