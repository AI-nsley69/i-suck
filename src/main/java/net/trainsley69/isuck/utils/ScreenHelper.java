package net.trainsley69.isuck.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.trainsley69.isuck.options.Option;

public class ScreenHelper extends Screen {
    protected ScreenHelper(Text title) {
        super(title);
    }

    public static Object[] singleRow(Option[] row, Screen screen, Screen parent, MinecraftClient client) {
        Object[] rowButtons = new Object[row.length+1];
        int length = row.length;
        int y = length / 2;
        int buttonW = Math.min(screen.width / 4, 200);
        int buttonH = 20;
        int buttonOffset = 4;
        int height = screen.height / 5;
        int width = screen.width / 2 - (buttonW / 2);

        for (int i = 0; i < row.length; i++) {
            Option option = row[i];
            int nextHeight = height + (i+1) * 24 - y;
            switch (option.getType()) {
                case BUTTON -> {
                    rowButtons[i] = new ButtonWidget(
                            width,
                            nextHeight,
                            buttonW,
                            buttonH,
                            option.getText(),
                            option::onToggle
                    );
                }
                case SLIDER -> {
                    rowButtons[i] = new SliderWidget(
                            width,
                            nextHeight,
                            buttonW,
                            buttonH,
                            option.getText(),
                            option.getSliderValue()
                    ) {
                        @Override
                        protected void updateMessage() {
                            option.onToggle(this);
                        }

                        @Override
                        protected void applyValue() {
                            option.applyValue(this.value);
                        }
                    };
                }
            }
        }

        int backButtonW = Math.min((int)(buttonW * 1.75), 300);
        // Back button
        rowButtons[row.length] = new ButtonWidget(screen.width / 2 - (backButtonW / 2), (height + (length + 1) * 24) - y, backButtonW, 20, ScreenTexts.BACK,
                btn -> {
                    assert client != null;
                    client.setScreen(parent);
                });

        return rowButtons;
    }

    public static Option.Type getWidgetType(Object widget) {
        if (widget instanceof SliderWidget) return Option.Type.SLIDER;
        if (widget instanceof ButtonWidget) return Option.Type.BUTTON;
        return Option.Type.NONE;
    }
}
