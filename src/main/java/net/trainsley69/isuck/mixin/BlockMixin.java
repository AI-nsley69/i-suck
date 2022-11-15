package net.trainsley69.isuck.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.trainsley69.isuck.utils.AutoReplantHelper.isCrop;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at=@At("RETURN"), method="onBreak")
    public void afterBreak(World world, BlockPos pos, BlockState state, PlayerEntity player, CallbackInfo ci) {
        if (!ISuck.config.AutoReplant || !world.isClient) return;
        Block block = state.getBlock();
        if (block instanceof CropBlock) {
            Item item = player.getMainHandStack().getItem();
            if (!isCrop(item)) return;
            MinecraftClient client = MinecraftClient.getInstance();
            BlockHitResult oldHit = (BlockHitResult)client.crosshairTarget;
            BlockHitResult hitMePapi = new BlockHitResult(oldHit.getPos(), Direction.UP, pos.down(), false);
            client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, hitMePapi);
        }
    }
}
