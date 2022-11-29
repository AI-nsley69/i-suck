package net.trainsley69.isuck.input;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.utils.FreecamHelper;
import net.trainsley69.isuck.utils.XRayHelper;

public class InputHandler {
    public static void handleKeyBindings() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;

        while (KeyBindings.TOGGLE_FREECAM.wasPressed()) {
            ISuck.config.Freecam = !ISuck.config.Freecam;
            FreecamHelper.onToggle();
            notifyUser("Freecam", ISuck.config.Freecam);
        }

        while (KeyBindings.TOGGLE_XRAY.wasPressed()) {
            ISuck.config.XRay = !ISuck.config.XRay;
            XRayHelper.changeSetting();
            notifyUser("XRay", ISuck.config.XRay);
        }

        while (KeyBindings.TOGGLE_FLYING.wasPressed()) {
            ISuck.config.Flying = !ISuck.config.Flying;
            notifyUser("Flying", ISuck.config.Flying);
        }
    }

    public static void notifyUser(String name, boolean option) {
        var text = Text.literal(name + ": ");
        var status = Text.literal(option ? "Enabled" : "Disabled");
        status.setStyle(status.getStyle().withFormatting(option ? Formatting.GREEN : Formatting.RED));
        text.append(status);
        FreecamHelper fakePlayer = FreecamHelper.getFakePlayer();
        if (fakePlayer != null) {
            fakePlayer.sendMessage(text, true);
        } else {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            assert player != null;
            player.sendMessage(text, true);
        }
    }
}
