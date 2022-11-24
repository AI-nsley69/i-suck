package net.trainsley69.isuck.utils;

import net.fabricmc.loader.api.FabricLoader;

public class ModDetection {
    public static boolean isSodiumPresent() {
        return FabricLoader.getInstance().isModLoaded("sodium");
    }
}
