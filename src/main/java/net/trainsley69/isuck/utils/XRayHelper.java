package net.trainsley69.isuck.utils;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.trainsley69.isuck.ISuck;

import java.util.Set;

public class XRayHelper {
    private static Set allowedBlocks = Set.of(
            Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.LAPIS_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.ANCIENT_DEBRIS
    );

    public static boolean isAllowed(Block block) {
        return allowedBlocks.contains(block);
    }

    public static void changeSetting() {
        MinecraftClient client = MinecraftClient.getInstance();
        // Change chunk culling, ambient occlusion and then reload the world
        client.chunkCullingEnabled = !ISuck.config.XRay;
        client.worldRenderer.reload();
    }
}
