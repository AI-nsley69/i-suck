package net.trainsley69.isuck.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.utils.GlowHelper;
import net.trainsley69.isuck.utils.GlowHelper.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At("HEAD"), method = "isGlowing", cancellable = true)
    private void isGlowing(CallbackInfoReturnable<Boolean> cr) {
        if (ISuck.config.EntityGlow) {
            EntityType entity = GlowHelper.getEntityType((Entity) (Object)this);
            if (entity != EntityType.Invalid) cr.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getTeamColorValue", cancellable = true)
    private void getTeamColorValue(CallbackInfoReturnable<Integer> cr) {
        if (ISuck.config.EntityGlow) {
            EntityType type = GlowHelper.getEntityType((Entity) (Object) this);
            int color = GlowHelper.getGlowColor(type);
            if (color > 0) cr.setReturnValue(color);
        }
    }

    @Inject(at = @At("HEAD"), method = "getJumpVelocityMultiplier", cancellable = true)
        private void getJumpVelocityMultiplier(CallbackInfoReturnable<Float> cr) {
            if ((Entity)(Object)this instanceof ClientPlayerEntity && ISuck.config.JumpHack > 0) {
                cr.setReturnValue(1.2f + (float)ISuck.config.JumpHack);
            }
        }
}
