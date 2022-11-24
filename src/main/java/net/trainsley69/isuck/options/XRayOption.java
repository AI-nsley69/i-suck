package net.trainsley69.isuck.options;

import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.utils.XRayHelper;

public class XRayOption extends Option {
    public XRayOption(String name, Type type) {
        super(name, type);
    }

    public boolean getValue() {
        return ISuck.config.XRay;
    }

    public void setValue() {
        ISuck.config.XRay = !ISuck.config.XRay;
        this.setText();
    }

    public void onToggle(ButtonWidget btn) {
        this.setValue();
        btn.setMessage(this.getText());
        XRayHelper.changeSetting();
    }

    @Override
    public void setText() {
        this.text = Text.literal(this.name + ": " +
                (ISuck.config.XRay ? "Enabled" : "Disabled")
        );
    }
}
