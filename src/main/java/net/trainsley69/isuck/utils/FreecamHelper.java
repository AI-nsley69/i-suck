package net.trainsley69.isuck.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.network.Packet;
import net.minecraft.stat.StatHandler;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameMode;
import net.trainsley69.isuck.ISuck;

import java.util.UUID;

public class FreecamHelper extends ClientPlayerEntity {
    private static FreecamHelper fakePlayer;

    private static GameMode oldGameMode;

    private final static ClientPlayNetworkHandler networkHandler = new ClientPlayNetworkHandler(MinecraftClient.getInstance(), MinecraftClient.getInstance().currentScreen, MinecraftClient.getInstance().getNetworkHandler().getConnection(), new GameProfile(UUID.randomUUID(), "ISuck"), MinecraftClient.getInstance().createTelemetrySender()) {
        @Override
        public void sendPacket(Packet<?> packet) {
        }
    };

    public FreecamHelper(MinecraftClient client, ClientWorld world, ClientPlayNetworkHandler networkHandler, StatHandler stats, ClientRecipeBook recipeBook, boolean lastSneaking, boolean lastSprinting) {
        super(client, world, networkHandler, stats, recipeBook, lastSneaking, lastSprinting);
    }

    @Override
    public boolean isSpectator() {
        return true;
    }

    public static void create() {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        // Create a new fakePlayer and copy the position and rotation from the player
        fakePlayer = new FreecamHelper(client, client.world, networkHandler, client.player.getStatHandler(), client.player.getRecipeBook(), false, false);
        fakePlayer.copyPositionAndRotation(client.player);
        // Add fakePlayer to the client's world, set camera entity to it and disable hand render
        client.player.clientWorld.addEntity(fakePlayer.getId(), fakePlayer);
        client.setCameraEntity(fakePlayer);
        client.gameRenderer.setRenderHand(false);
        client.chunkCullingEnabled = false;
        // New input for fakePlayer
        fakePlayer.input = new KeyboardInput(client.options);
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
                client.player.clientWorld.removeEntity(fakePlayer.getId(), RemovalReason.DISCARDED);
            }
            fakePlayer = null;
        }
    }

    public static void updateMovement() {
        if (fakePlayer == null) return;
        MinecraftClient client = MinecraftClient.getInstance();
        FreecamHelper fakePlayer = FreecamHelper.getFakePlayer();

        assert client.player != null;
        fakePlayer.setYaw(client.player.getYaw());
        fakePlayer.setPitch(client.player.getPitch());
    }

    public static FreecamHelper getFakePlayer() {
        return fakePlayer;
    }

    // Prevent falling
    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
    }

    // Prevents slow down from ladders/vines.
    @Override
    public boolean isClimbing() {
        return false;
    }

    // Prevents slow down from water.
    @Override
    public boolean isTouchingWater() {
        return false;
    }

    @Override
    public void tickMovement() {
        fakePlayer.noClip = true;
        fakePlayer.getAbilities().flying = true;
        fakePlayer.getAbilities().setFlySpeed(0.15f);
        fakePlayer.onGround = false;
        super.tickMovement();
    }
}
