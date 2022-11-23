package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {
    @Inject(at=@At("HEAD"), method = "isAmbientOcclusionEnabled()Z", cancellable = true)
    private static void isAmbientOcclusionEnabled(CallbackInfoReturnable<Boolean> cr) {
        if (!ISuck.config.XRay) return;
        cr.setReturnValue(false);
        cr.cancel();
    }

    @Inject(at = @At("HEAD"), method = "joinWorld")
    private void joinWorld(ClientWorld world, CallbackInfo ci) {
        ISuck.Shared.justJoined = true;
    }
}
