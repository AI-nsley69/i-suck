package net.trainsley69.isuck;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;

public class ISuck implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("i-suck");

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("De-sucking at gaem..");
	}

	public class Config {
		private static boolean AutoFish = false;

		public static boolean isAutoFish() {
			return AutoFish;
		}

		public static void setAutoFish(boolean autoFish) {
			AutoFish = autoFish;
		}

		private static boolean Flying = false;
		public static boolean isFlying() { return Flying; }
		public static void setFlying(boolean flying) { Flying = flying; }
	}
}
