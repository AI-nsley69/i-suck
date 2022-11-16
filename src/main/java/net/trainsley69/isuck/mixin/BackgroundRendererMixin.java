package net.trainsley69.isuck.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.CameraSubmersionType;
import net.minecraft.client.render.FogShape;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Inject(at = @At("HEAD"), method = "applyFog", cancellable = true)
    private static void applyFog(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        // Return if nofog is not enabled
        if (!ISuck.config.NoFog) return;
        // Get entity and submersion type
        Entity entity = camera.getFocusedEntity();
        CameraSubmersionType cameraSubmersionType = camera.getSubmersionType();
        // If the player has darkness or blindness, return early
        if (((LivingEntity)entity).hasStatusEffect(StatusEffects.BLINDNESS) || ((LivingEntity)entity).hasStatusEffect(StatusEffects.DARKNESS)) return;
        // Cancel the function and set our own values for fogstart and fogend
        ci.cancel();
        if (cameraSubmersionType == CameraSubmersionType.NONE) {
            RenderSystem.setShaderFogStart(ISuck.Shared.NoFog.FOG_START);
            RenderSystem.setShaderFogStart(ISuck.Shared.NoFog.FOG_END);
        } else {
            ISuck.Shared.NoFog.SUBMERSED.FOG_END = viewDistance * 0.5f;
            RenderSystem.setShaderFogStart(ISuck.Shared.NoFog.SUBMERSED.FOG_START);
            RenderSystem.setShaderFogStart(ISuck.Shared.NoFog.SUBMERSED.FOG_END);
        }
        RenderSystem.setShaderFogShape(FogShape.CYLINDER);
    }
}
