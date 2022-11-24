package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.trainsley69.isuck.ISuck;

public class JumpHackOption extends Option {

    public JumpHackOption(String name, Type type) {
        super(name, type);
    }

    public double getSliderValue() {
        return ISuck.config.JumpHack;
    }

    public void setValue(Float value) {
        ISuck.config.JumpHack = value;
        this.setText();
    }

    public void onToggle(SliderWidget btn) {
        this.setText();
        btn.setMessage(this.getText());
    }
    @Override
    public void setText() {
        this.text = Text.literal(this.name + ": " +
                (this.getSliderValue() != 0 ? roundNumber(this.getSliderValue() / 4 * 10 + 1.5) : 0) +
                " blocks");
    }

    private double roundNumber(double n) {
        return Math.round(n * 10) / 10;
    }

    @Override
    public void applyValue(double value) {
        ISuck.config.JumpHack = MathHelper.clamp((float)value / 2 , 0.0f, 0.5f);
        this.setText();
    }
}
