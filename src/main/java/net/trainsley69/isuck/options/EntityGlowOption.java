package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;

public class EntityGlowOption extends Option {
    public EntityGlowOption(String name, Type type) {
        super(name, type);
    }

    public boolean getValue() {
        return ISuck.config.EntityGlow;
    }

    public void setValue() {
        ISuck.config.EntityGlow = !ISuck.config.EntityGlow;
        this.setText();
    }

    public void onToggle(ButtonWidget btn) {
        this.setValue();
        btn.setMessage(this.getText());
    }

    @Override
    public void setText() {
        this.text = Text.literal(this.name + ": " +
                (ISuck.config.EntityGlow ? "Enabled" : "Disabled")
        );
    }
}
