package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private double antiKick = -0.04;
    private int tickCounter = 0;
    private final int tickLimit = 40;

    private double restoreHeight = 0.04;
    private int restoreCounter = 0;
    private int restoreLimit = 20;
    private boolean wasTriggered = false;

    @Shadow
    private PlayerAbilities abilities;

    @Inject(method="tick()V", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (ISuck.Config.isFlying()) {
            MinecraftClient client = MinecraftClient.getInstance();
            // Allow creative flying by setting the ability to true on the client if we're not on the ground
            if (!client.player.isOnGround()) abilities.flying = true;
            // Get the client and the players current velocity
            Vec3d velocity = client.player.getVelocity();
            double motionY = velocity.y;
            // Set the tick counter to 2 ticks if player is shifting, since this already resets on anticheat
            if (client.options.sneakKey.isPressed()) {
                motionY += antiKick;
                tickCounter = 2;
            }
            // If we've hit the tick counter, reduce speed by the antiKick amount
            if (tickCounter == tickLimit) {
                motionY = antiKick;
                tickCounter = 0;
                wasTriggered = true;
            }

            if (wasTriggered) {
                restoreCounter++;
                if (restoreCounter == restoreLimit) {
                    if (velocity.y == 0.0) motionY += restoreHeight;
                    restoreCounter = 0;
                    wasTriggered = false;
                }
            }
            // Set the new y speed and update the tickCounter
            client.player.setVelocity(velocity.x, motionY, velocity.z);
            tickCounter++;
            ISuck.LOGGER.info("Tick Counter: " + restoreCounter);
        } else {
            abilities.flying = false;
        }
    }
}
