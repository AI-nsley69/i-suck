package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;

public class AutoReplantOption extends Option {
    public AutoReplantOption(String name, Type type) {
        super(name, type);
    }

    public boolean getValue() {
        return ISuck.config.AutoReplant;
    }

    public void setValue() {
        ISuck.config.AutoReplant = !ISuck.config.AutoReplant;
        this.setText();
    }

    public void onToggle(ButtonWidget btn) {
        this.setValue();
        btn.setMessage(this.getText());
    }

    @Override
    public void setText() {
        this.text = Text.literal(this.name + ": " +
                (ISuck.config.AutoReplant ? "Enabled" : "Disabled")
        );
    }
}
