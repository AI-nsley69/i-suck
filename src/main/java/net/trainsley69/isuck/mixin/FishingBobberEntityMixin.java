package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.Hand;
import net.trainsley69.isuck.ISuck;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    @Shadow
    private boolean caughtFish;

    @Shadow @Nullable
    public abstract PlayerEntity getPlayerOwner();

    // Only pull once the fish
    @Inject(method = "onTrackedDataSet", at = @At("TAIL"))
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (caughtFish && ISuck.config.AutoFish && this.getPlayerOwner() == client.player) {
            if (client.interactionManager != null) {
                client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
            }
            ISuck.Shared.recastDelay = 6;
        }
    }
}