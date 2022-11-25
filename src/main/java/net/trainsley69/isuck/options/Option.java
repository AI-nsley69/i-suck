package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public class Option {
    String name;
    Type type;
    Text text = Text.literal("Placeholder");

    public Option(String name, Type type) {
        this.name = name;
        this.type = type;
        this.setText();
    }

    public Type getType() {
        return this.type;
    }

    public Text getText() {
        return this.text;
    }

    public void onToggle(ButtonWidget btn) {
    }

    public void onToggle(SliderWidget btn) {
    }

    public void setText() {
    }

    public void applyValue(double value) {
    }

    public double getSliderValue() {
        return 0;
    }

    public enum Type {
        SLIDER,
        BUTTON,
        NONE,
    }
}
