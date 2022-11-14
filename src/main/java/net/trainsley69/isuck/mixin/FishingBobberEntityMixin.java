package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.util.Hand;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Mixin(FishingBobberEntity.class)
public abstract class FishingBobberEntityMixin {
    ScheduledExecutorService ses = Executors.newScheduledThreadPool(1);
    @Shadow
    private boolean caughtFish;

    // Only pull once the fish
    @Inject(method = "onTrackedDataSet", at = @At("TAIL"))
    public void onTrackedDataSet(TrackedData<?> data, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (caughtFish && ISuck.config.AutoFish) {
            Runnable recast = () -> client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
            client.interactionManager.interactItem(client.player, Hand.MAIN_HAND);
            ses.schedule(recast, 300, TimeUnit.MILLISECONDS);
            ses.shutdown();
        }
    }
}