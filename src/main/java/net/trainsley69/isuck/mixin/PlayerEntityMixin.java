package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    private double ANTI_KICK = -0.04;
    double ACCEL = 0.1;
    private int CURRENT = 0;
    private int LIMIT = 40;

    @Inject(method="tick()V", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {
        if (ISuck.Config.isFlying()) {
            CURRENT++;
            MinecraftClient client = MinecraftClient.getInstance();
            Vec3d velocity = client.player.getVelocity();
            double motionY = client.options.jumpKey.isPressed() ? 0.5 : client.options.sneakKey.isPressed() ? -0.3 : 0;
            if (CURRENT == LIMIT) motionY = ANTI_KICK;
            if (CURRENT > LIMIT && motionY == 0) motionY = -1 * ANTI_KICK;
            if (CURRENT > LIMIT) CURRENT = 0;


            client.player.setVelocity(velocity.x, motionY, velocity.z);
        }
    }
}
