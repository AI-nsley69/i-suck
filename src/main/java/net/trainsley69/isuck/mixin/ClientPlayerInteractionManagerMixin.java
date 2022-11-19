package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(at = @At("HEAD"), method = "attackEntity", cancellable = true)
    private void attackEntity(PlayerEntity player, Entity target, CallbackInfo ci) {
        if (!ISuck.config.NoAbuse) return;
        if (target instanceof TameableEntity && ((TameableEntity) target).isTamed()) ci.cancel();
        if (target instanceof VillagerEntity) ci.cancel();
    }

    @Shadow
    private int blockBreakingCooldown;

    @Inject(at = @At("HEAD"), method="tick")
    private void tick(CallbackInfo ci) {
        if (ISuck.config.FastBreak) this.blockBreakingCooldown = 0;
    }

    @Shadow
    private float currentBreakingProgress;
    @Inject(at = @At("HEAD"), method = "updateBlockBreakingProgress")
    private void updateBlockBreakingProgress(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cr) {
        if (!ISuck.config.FastBreak) return;
        if (this.currentBreakingProgress >= 1) return;
        Action action = Action.STOP_DESTROY_BLOCK;
        MinecraftClient.getInstance().getNetworkHandler().sendPacket(new PlayerActionC2SPacket(action, pos, direction));
    }
}
