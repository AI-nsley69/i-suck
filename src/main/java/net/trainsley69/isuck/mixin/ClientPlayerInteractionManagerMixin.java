package net.trainsley69.isuck.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.trainsley69.isuck.ISuck;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static net.trainsley69.isuck.utils.AutoReplantHelper.isCrop;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method="breakBlock", at=@At("RETURN"))
    private void breakBlock(BlockPos pos, CallbackInfo ci, CallbackInfoReturnable<?> cr) {
        boolean result = (boolean) cr.getReturnValue();
        if (!result) return;
        if (!ISuck.config.AutoReplant) return;
        MinecraftClient client = MinecraftClient.getInstance();
        // World world = client.world;
        PlayerEntity player = client.player;
        // BlockState state = world.getBlockState(pos);


        ISuck.LOGGER.info("Mm yes, AutoReplant is enabled");
        /* Block block = state.getBlock();
        if (block instanceof CropBlock) {
            ISuck.LOGGER.info("This is a cropblock yes");
            Item item = player.getMainHandStack().getItem();
            if (!isCrop(item)) return;
            ISuck.LOGGER.info("Hah! we're replantable BABYYYYYY");
            player.interact(player, Hand.MAIN_HAND);
        } */
    }
}
