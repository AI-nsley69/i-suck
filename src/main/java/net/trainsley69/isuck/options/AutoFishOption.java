package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;

public class AutoFishOption extends Option {
    public AutoFishOption(String name, Type type) {
        super(name, type);
    }

    public boolean getValue() {
        return ISuck.config.AutoFish;
    }

    public void setValue() {
        ISuck.config.AutoFish = !ISuck.config.AutoFish;
        this.setText();
    }

    public void onToggle(ButtonWidget btn) {
        this.setValue();
        btn.setMessage(this.getText());
    }

    @Override
    public void setText() {
        this.text = Text.literal(this.name + ": " +
                (ISuck.config.AutoFish ? "Enabled" : "Disabled")
        );
    }
}
