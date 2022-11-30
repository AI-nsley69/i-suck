package net.trainsley69.isuck.utils;

import com.mojang.authlib.GameProfile;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.network.Packet;
import net.minecraft.stat.StatHandler;
import net.minecraft.util.math.BlockPos;
import net.trainsley69.isuck.ISuck;

import java.util.UUID;

public class FakePlayer extends ClientPlayerEntity {

    private static FakePlayer instance;

    private final static ClientPlayNetworkHandler networkHandler = new ClientPlayNetworkHandler(MinecraftClient.getInstance(), MinecraftClient.getInstance().currentScreen, MinecraftClient.getInstance().getNetworkHandler().getConnection(), new GameProfile(UUID.randomUUID(), "ISuck"), MinecraftClient.getInstance().createTelemetrySender()) {
        @Override
        public void sendPacket(Packet<?> packet) {
        }
    };

    public FakePlayer(MinecraftClient client, ClientWorld world, ClientPlayNetworkHandler networkHandler, StatHandler stats, ClientRecipeBook recipeBook, boolean lastSneaking, boolean lastSprinting) {
        super(client, world, networkHandler, stats, recipeBook, lastSneaking, lastSprinting);
    }

    public static FakePlayer createPlayer() {
        MinecraftClient client = MinecraftClient.getInstance();
        assert client.player != null;
        FakePlayer fakePlayer = new FakePlayer(client, client.world, networkHandler, client.player.getStatHandler(), client.player.getRecipeBook(), false, false);
        fakePlayer.copyPositionAndRotation(client.player);
        fakePlayer.input = new KeyboardInput(client.options);
        instance = fakePlayer;
        fakePlayer.spawn(client.player);
        return getInstance();
    }

    public static FakePlayer getInstance() { return instance; }

    public void spawn(ClientPlayerEntity player) {
        player.clientWorld.addEntity(this.getId(), this);
        this.copyInventory(player);
        this.copyPlayerModel(player);
    }

    public void despawn() {
        discard();
        instance = null;
    }

    public void copyPlayerModel(ClientPlayerEntity player) {
        DataTracker playerModel = player.getDataTracker();
        DataTracker fakePlayerModel = this.getDataTracker();
        byte model = playerModel.get(PLAYER_MODEL_PARTS);
        fakePlayerModel.set(PLAYER_MODEL_PARTS, model);
    }
    public void copyInventory(ClientPlayerEntity player) {
        this.getInventory().clone(player.getInventory());
    }

    @Override
    protected void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition) {
        if (!ISuck.config.Freecam) super.fall(heightDifference, onGround, state, landedPosition);
    }

    // Prevents slow down from ladders/vines.
    @Override
    public boolean isClimbing() { return !ISuck.config.Freecam && super.isClimbing(); }

    // Prevents slow down from water.
    @Override
    public boolean isTouchingWater() {
        return !ISuck.config.Freecam && super.isTouchingWater();
    }

    @Override
    public void tickMovement() {
        if (ISuck.config.Freecam) {
            this.noClip = true;
            this.getAbilities().flying = true;
            this.getAbilities().setFlySpeed(0.15f);
            this.onGround = false;
            super.tickMovement();
        }
    }
}
