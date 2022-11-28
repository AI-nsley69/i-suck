package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.utils.FreecamHelper;

public class FreecamOption extends Option {
    public FreecamOption(String name, Type type) {
        super(name, type);
    }

    public boolean getValue() {
        return ISuck.config.Freecam;
    }

    public void setValue() {
        ISuck.config.Freecam = !ISuck.config.Freecam;
        this.setText();
    }

    public void onToggle(ButtonWidget btn) {
        this.setValue();
        btn.setMessage(this.getText());
        FreecamHelper.onToggle();
    }

    @Override
    public void setText() {
        this.text = Text.literal(this.name + ": " +
                (ISuck.config.Freecam ? "Enabled" : "Disabled")
        );
    }
}
