package net.trainsley69.isuck.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.trainsley69.isuck.ISuck;
import net.trainsley69.isuck.utils.FakePlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {
    // From https://github.com/hashalite/Freecam/blob/main/src/main/java/net/xolt/freecam/mixins/WorldRendererMixin.java
    // Shows the original player
    @Shadow
    private BufferBuilderStorage bufferBuilders;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/WorldRenderer;checkEmpty(Lnet/minecraft/client/util/math/MatrixStack;)V", ordinal = 0))
    private void onRender(MatrixStack matrices, float tickDelta, long limitTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightmapTextureManager lightmapTextureManager, Matrix4f positionMatrix, CallbackInfo ci) {
        if (shouldRenderPlayer()) {
            Vec3d fakePos = FakePlayer.getInstance().getPos();
            renderEntity(MinecraftClient.getInstance().player, fakePos.x, fakePos.y, fakePos.z, tickDelta, matrices, bufferBuilders.getEntityVertexConsumers());
        }
    }

    private boolean shouldRenderPlayer() {
        return ISuck.config.Freecam;
    }

    @Shadow
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers) {
    }
}
