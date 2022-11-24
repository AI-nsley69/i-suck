package net.trainsley69.isuck;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.trainsley69.isuck.options.*;

public class ISuckScreen extends Screen {
    private final Screen parent;
    private final GameOptions settings;

    Option[] row1 = {
            new AutoFishOption("AutoFish", Option.Type.BUTTON),
            new FlyHackOption("FlyHack", Option.Type.BUTTON),
            new XRayOption("XRay", Option.Type.BUTTON),
            new NoAbuseOption("NoAbuse", Option.Type.BUTTON),
            new JumpHackOption("JumpHack", Option.Type.SLIDER)
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
        int length = Math.max(row1.length, row2.length);
        int y = length / 2;
        int buttonW = Math.min(this.width / 4, 200);
        int buttonH = 20;
        int buttonOffset = 4;
        int height = this.height / 5;
        int width1 = this.width / 3 - buttonOffset - (buttonW / 2);
        int width2 = this.width - this.width / 3 - buttonOffset - (buttonW / 2);

        // LEFT ROW
        for (int i = 0; i < row1.length; i++) {
            Option option = row1[i];
            int nextHeight = height + (i+1) * 24 - y;
            switch (option.getType()) {
                case BUTTON -> {
                    this.addDrawableChild(new ButtonWidget(
                            width1,
                            nextHeight,
                            buttonW,
                            buttonH,
                            option.getText(),
                            option::onToggle
                    ));
                }
                case SLIDER -> {
                    this.addDrawableChild(new SliderWidget(
                            width1,
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
                    });
                }
            }
        }
        // RIGHT ROW
        for (int i = 0; i < row2.length; i++) {
            Option option = row2[i];
            int nextHeight = height + (i+1) * 24 - y;
            switch (option.getType()) {
                case BUTTON -> {
                    this.addDrawableChild(new ButtonWidget(
                            width2,
                            nextHeight,
                            buttonW,
                            buttonH,
                            option.getText(),
                            option::onToggle
                    ));
                }
                case SLIDER -> {
                    this.addDrawableChild(new SliderWidget(
                            width2,
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
                    });
                }
            }
        }

        int backButtonW = Math.min((int)(buttonW * 1.75), 300);
        // Back button
        this.addDrawableChild(new ButtonWidget(this.width / 2 - (backButtonW / 2), (height + (length + 1) * 24) - y, backButtonW, 20, ScreenTexts.BACK,
                btn -> {
                    assert this.client != null;
                    this.client.setScreen(this.parent);
                }));
    }

    private Text getText(String text, Boolean toggled) {
        return Text.literal(text + ": " + (toggled ? "Enabled" : "Disabled"));
    }
}
