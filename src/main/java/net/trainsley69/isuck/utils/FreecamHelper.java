package net.trainsley69.isuck.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.world.GameMode;
import net.trainsley69.isuck.ISuck;

public class FreecamHelper {
    private static FakePlayer fakePlayer;

    private static boolean wasSneaking;

    public static void create() {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        // Create a new fakePlayer and copy the position and rotation from the player
        fakePlayer = FakePlayer.createPlayer();
        client.setCameraEntity(fakePlayer);
        client.gameRenderer.setRenderHand(false);
        client.chunkCullingEnabled = false;

        wasSneaking = client.player.input.sneaking;
    }

    public static void onToggle() {
        if (ISuck.config.Freecam) create();
        else {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client.interactionManager.getCurrentGameMode() != GameMode.SPECTATOR) {
                client.gameRenderer.setRenderHand(true);
            }
            client.setCameraEntity(client.player);
            client.chunkCullingEnabled = true;
            if (client.player != null) {
                fakePlayer.despawn();
            }
            fakePlayer = null;
        }
    }

    public static void updateMovement() {
        if (fakePlayer == null) return;
        MinecraftClient client = MinecraftClient.getInstance();
        FakePlayer fakePlayer = FakePlayer.getInstance();

        assert client.player != null && fakePlayer != null;

        client.player.input.sneaking = wasSneaking;
    }
}
