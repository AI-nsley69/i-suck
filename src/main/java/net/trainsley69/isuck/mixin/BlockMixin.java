package net.trainsley69.isuck.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.trainsley69.isuck.ISuck;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.trainsley69.isuck.utils.AutoReplantHelper.isCrop;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(at=@At("RETURN"), method="afterBreak")
    public void afterBreak(World world, PlayerEntity player, BlockPos pos, BlockState state, @Nullable BlockEntity blockEntity, ItemStack stack, CallbackInfo ci) {
        ISuck.LOGGER.info("HELLO WORLD!");
        if (!ISuck.config.AutoReplant || !world.isClient) return;
        ISuck.LOGGER.info("Mm yes, AutoReplant is enabled");
        Block block = state.getBlock();
        if (block instanceof CropBlock) {
            ISuck.LOGGER.info("This is a cropblock yes");
            Item item = player.getMainHandStack().getItem();
            if (!isCrop(item)) return;
            ISuck.LOGGER.info("Hah! we're replantable BABYYYYYY");
            player.interact(player, Hand.MAIN_HAND);
        }
    }
}
