package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;

public class NoFogOption extends Option {
    public NoFogOption(String name, Type type) {
        super(name, type);
    }

    public boolean getValue() {
        return ISuck.config.NoFog;
    }

    public void setValue() {
        ISuck.config.NoFog = !ISuck.config.NoFog;
        this.setText();
    }

    public void onToggle(ButtonWidget btn) {
        this.setValue();
        btn.setMessage(this.getText());
        ISuck.reloadRenderer();
    }

    @Override
    public void setText() {
        this.text = Text.literal(this.name + ": " +
                (ISuck.config.NoFog ? "Enabled" : "Disabled")
        );
    }
}
