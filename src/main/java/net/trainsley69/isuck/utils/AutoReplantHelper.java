package net.trainsley69.isuck.utils;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.Set;

public class AutoReplantHelper {
    private static final Set<Item> acceptedItems = Set.of(
            Items.WHEAT_SEEDS,
            Items.BEETROOT_SEEDS,
            Items.CARROT,
            Items.POTATO
    );

    public static boolean isCrop(Item item) {
        return acceptedItems.contains(item);
    }
}
