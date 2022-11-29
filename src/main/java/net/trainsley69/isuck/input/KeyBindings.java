package net.trainsley69.isuck.input;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.trainsley69.isuck.ISuck;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class KeyBindings {
    private static final Map<String, KeyBinding> KEY_BINDINGS = new LinkedHashMap<>();

    public static final String MAIN;
    public static final KeyBinding TOGGLE_FREECAM;
    public static final KeyBinding TOGGLE_XRAY;
    public static final KeyBinding TOGGLE_FLYING;

    private static KeyBinding registerKeyBind(KeyBinding keyBinding, String name) {
        KEY_BINDINGS.putIfAbsent(name, keyBinding);
        return keyBinding;
    }

    public static Collection<KeyBinding> getKeyBindings() {
        return Collections.unmodifiableCollection(KEY_BINDINGS.values());
    }

    public static int getGlfwKey(String key) {
        return InputUtil.fromTranslationKey(key).getCode();
    }

    static {
        MAIN = "I Suck";
        TOGGLE_FREECAM = registerKeyBind(new KeyBinding("Freecam Toggle", getGlfwKey(ISuck.keybinds.Freecam), MAIN), "freecam");
        TOGGLE_XRAY = registerKeyBind(new KeyBinding("XRay Toggle", getGlfwKey(ISuck.keybinds.XRay), MAIN), "xray");
        TOGGLE_FLYING = registerKeyBind(new KeyBinding("Flyhack Toggle", getGlfwKey(ISuck.keybinds.Flying), MAIN), "flyhack");
    }
}
