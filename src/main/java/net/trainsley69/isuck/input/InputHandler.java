package net.trainsley69.isuck.input;

import net.minecraft.client.MinecraftClient;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.utils.FreecamHelper;

public class InputHandler {
    public static void handleKeyBindings() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        while (KeyBindings.TOGGLE_FREECAM.wasPressed()) {
            ISuck.config.Freecam = !ISuck.config.Freecam;
            FreecamHelper.onToggle();
        }
    }
}
