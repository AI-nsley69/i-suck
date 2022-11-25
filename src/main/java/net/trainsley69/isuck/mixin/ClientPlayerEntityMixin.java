package net.trainsley69.isuck.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.trainsley69.isuck.ISuck;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {
    private int tickCounter = 0;

    private int restoreCounter = 0;
    private boolean wasTriggered = false;

    public ClientPlayerEntityMixin(World world, BlockPos pos, float yaw, GameProfile gameProfile, @Nullable PlayerPublicKey publicKey) {
        super(world, pos, yaw, gameProfile, publicKey);
    }

    @Inject(method="tick()V", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        PlayerAbilities abilities = this.getAbilities();
        if (ISuck.config.Flying) flyingLogic(abilities);
        else {
            abilities.allowFlying = false;
            abilities.flying = false;
        }
        if (ISuck.config.AutoFish) checkRecast();

        if (ISuck.config.AutoReplant) checkReplant();

        if (ISuck.config.DolphinHack) dolphinLogic();
    }

    private void dolphinLogic() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!client.player.isWet() || client.player.isSneaking()) return;

        Vec3d v = client.player.getVelocity();
        client.player.setVelocity(v.x, v.y + 0.04, v.z);
    }

    private void flyingLogic(PlayerAbilities abilities) {
        // Check if we need to enable flying on join
        joinChecks(abilities);
        // Do the rest of the flying check
        MinecraftClient client = MinecraftClient.getInstance();
        // Allow creative flying by setting the ability to true on the client if we're not on the ground
        abilities.allowFlying = true;
        abilities.setFlySpeed(0.075f);
        Vec3d velocity = this.getVelocity();
        double motionY = velocity.y;
        // Set the tick counter to 2 ticks if player is shifting, since this already resets on anticheat
        double antiKick = -0.04;
        if (client.options.sneakKey.isPressed()) {
            if (abilities.flying) motionY = motionY / 2;
            tickCounter = 2;
        }

        if (client.options.jumpKey.isPressed() && abilities.flying) motionY *= 1.3;
        // If we've hit the tick counter, reduce speed by the antiKick amount
        int tickLimit = 40;
        if (tickCounter == tickLimit) {
            motionY = antiKick;
            tickCounter = 0;
            wasTriggered = true;
        }

        if (wasTriggered) {
            restoreCounter++;
            int restoreLimit = 20;
            if (restoreCounter == restoreLimit) {
                double restoreHeight = 0.04;
                if (velocity.y == 0.0) motionY += restoreHeight;
                restoreCounter = 0;
                wasTriggered = false;
            }
        }
        // Set the new y speed and update the tickCounter
        this.setVelocity(velocity.x, motionY, velocity.z);
         if (abilities.flying) tickCounter++;
         else tickCounter = 2;
    }

    public void joinChecks(PlayerAbilities abilities) {
        if (ISuck.Shared.justJoined && !this.isOnGround()) {
            abilities.flying = true;
            ISuck.Shared.justJoined = false;
        }
    }

    private void checkReplant() {
        if (ISuck.Shared.replantDelay >= 0) ISuck.Shared.replantDelay--;
        if (ISuck.Shared.replantDelay == 0) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.interactionManager.interactItem(this, Hand.MAIN_HAND);
        }
    }

    private void checkRecast() {
        if (ISuck.Shared.recastDelay > 0) ISuck.Shared.recastDelay--;
        if (ISuck.Shared.recastDelay == 1 && this.getMainHandStack().getItem() == Items.FISHING_ROD) {
            MinecraftClient client = MinecraftClient.getInstance();
            client.interactionManager.interactItem(this, Hand.MAIN_HAND);
        }
    }

    @Inject(at = @At("HEAD"), method = "requestRespawn")
    private void requestRespawn(CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        Vec3d pos = client.player.getPos();

        var deathMessage = Text.literal(
                "You died at "

        );
        deathMessage.setStyle(deathMessage.getStyle().withFormatting(Formatting.BOLD, Formatting.GRAY));

        var coordsMessage = Text.literal(
                Math.round(pos.x)
                + ", " + Math.round(pos.y)
                + ", " + Math.round(pos.z)
        );
        coordsMessage.setStyle(coordsMessage.getStyle().withFormatting(Formatting.GOLD, Formatting.UNDERLINE));

        deathMessage.append(coordsMessage);
        client.player.sendMessage(deathMessage, false);
    }
}