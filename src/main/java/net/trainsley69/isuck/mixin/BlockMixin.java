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
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.trainsley69.isuck.utils.AutoReplantHelper.isCrop;
import static net.trainsley69.isuck.utils.XRayHelper.isAllowed;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at=@At("HEAD"), method="onBroken")
    private void afterBreak(WorldAccess world, BlockPos pos, BlockState state, CallbackInfo ci) {
        // Get the client and world
        MinecraftClient client = MinecraftClient.getInstance();
        World level = client.world;
        // Return if the option isn't toggled or if it's not the client
        if (ISuck.config.AutoReplant || (level != null && level.isClient)) replantLogic(client, state, pos);
    }

    private void replantLogic(MinecraftClient client, BlockState state, BlockPos pos) {
        // Get the player entity and block
        PlayerEntity player = client.player;
        Block block = state.getBlock();
        // Check if the block is a crop, then get the main item and see if it's a valid seed
        if (block instanceof CropBlock) {
            assert player != null;
            Item item = player.getMainHandStack().getItem();
            if (!isCrop(item)) return;
            // Make a new BlockHitResult with the block below
            BlockHitResult oldHit = (BlockHitResult) client.crosshairTarget;
            assert oldHit != null;
            BlockHitResult hitMePapi = new BlockHitResult(oldHit.getPos(), Direction.UP, pos.offset(Direction.Axis.Y, -1), oldHit.isInsideBlock());
            // interact with the block using the new blockhitresult
            if (client.interactionManager != null) {
                client.interactionManager.interactBlock(client.player, Hand.MAIN_HAND, hitMePapi);
            }
        }
    }

    @Inject(at=@At("RETURN"), method="shouldDrawSide", cancellable = true)
    private static void shouldDrawSide(BlockState state, BlockView world, BlockPos pos, Direction side, BlockPos otherPos, CallbackInfoReturnable<Boolean> cr) {
        if (ISuck.config.XRay) cr.setReturnValue(isAllowed(state.getBlock()));
    }
}
