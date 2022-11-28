package net.trainsley69.isuck.input;

import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class KeyBindings {
    private static final Map<String, KeyBinding> KEY_BINDINGS = new LinkedHashMap<>();

    public static final String MAIN;
    public static final KeyBinding TOGGLE_FREECAM;

    private static KeyBinding registerKeyBind(KeyBinding keyBinding, String name) {
        KEY_BINDINGS.putIfAbsent(name, keyBinding);
        return keyBinding;
    }

    public static Collection<KeyBinding> getKeyBindings() {
        return Collections.unmodifiableCollection(KEY_BINDINGS.values());
    }

    static {
        MAIN = "I Suck";
        TOGGLE_FREECAM = registerKeyBind(new KeyBinding("key.isuck.freecam", GLFW.GLFW_KEY_G, MAIN), "freecam");
    }
}
