package net.trainsley69.isuck.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.command.argument.BlockArgumentParser;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.ToolItem;
import net.minecraft.network.packet.c2s.play.ClickSlotC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
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

    @Inject(at = @At("HEAD"), method = "attackBlock")
    private void attackBlock(BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cr) {
        if (!ISuck.config.AutoTool) return;
        // Get the client, inventory and blockstate
        MinecraftClient client = MinecraftClient.getInstance();
        BlockState state = client.world.getBlockState(pos);
        PlayerInventory inventory = client.player.getInventory();
        // Try to find the most efficient item
        ItemStack mostEfficientItem = getMostEfficientTool(inventory, state);
        // Set current slot to the most efficient item
        if (mostEfficientItem != ItemStack.EMPTY) {
            // Get the best hotbar slot
            int bestSlot = getBestSlot(mostEfficientItem, inventory);
            if (bestSlot == -1) return;
            // Move around the slots
            inventory.selectedSlot = bestSlot;
            client.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(inventory.selectedSlot));
        }
    }

    private ItemStack getMostEfficientTool(PlayerInventory inventory, BlockState state) {
        ItemStack mostEfficientItem = ItemStack.EMPTY;
        float mostEfficientSpeed = 0.0f;
        int max = inventory.size();
        for (int i = 0; i < max; i++) {
            ItemStack item = inventory.getStack(i);
            if (item.isEmpty()) continue;
            if (!(item.getItem() instanceof MiningToolItem)) continue;
            float efficiency = item.getMiningSpeedMultiplier(state);
            if (!(efficiency > mostEfficientSpeed)) continue;
            mostEfficientItem = item;
            mostEfficientSpeed = efficiency;
        }

        return mostEfficientItem;
    }

    private int getBestSlot(ItemStack item, PlayerInventory inventory) {
        int slot = inventory.getSlotWithStack(item);
        if (slot >= 9 || slot == -1) return -1;
        return slot;
    }
}
