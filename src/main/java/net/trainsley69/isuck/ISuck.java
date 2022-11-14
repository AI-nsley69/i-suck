package net.trainsley69.isuck;

import net.fabricmc.api.ModInitializer;
import net.minecraft.resource.ResourceManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;
import java.util.Objects;

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
		try {
			loadConfig();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		LOGGER.info("Finished loading config");
	}

	private void loadConfig() throws IOException, ParseException {
		// Get the config file
		String fileName = "i-suck.json";
		Path configPath = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir();
		File configFile = new File(configPath.toFile(), fileName);
		// Create the file if it doesn't exist
		if (!configFile.exists()) {
			try (InputStream input = ISuck.class.getResourceAsStream("/assets/i-suck.json")) {
				Files.createDirectories(configPath);
				Files.copy(Objects.requireNonNull(input, "Jar or class loader is bad."), configFile.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		// If the file exists, is not a folder and is readable, read it
		if (configFile.exists() && configFile.isFile() && configFile.canRead()) {
			Object conf = new JSONParser().parse(new FileReader(configFile));
			JSONObject confJson = (JSONObject) conf;

			Config.AutoFish = (Boolean) confJson.get("AutoFish");
			Config.Flying = (Boolean) confJson.get("Flying");
			Config.Fullbright = (Boolean) confJson.get("Fullbright");
		}
	}

	public static void writeConfig() {
		// Config file
		String fileName = "i-suck.json";
		Path configPath = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir();
		File configFile = new File(configPath.toFile(), fileName);

		JSONObject saveObj = new JSONObject();

		saveObj.put("AutoFish", Config.isAutoFish());
		saveObj.put("Flying", Config.isFlying());
		saveObj.put("Fullbright", Config.isFullbright());
		try {
			FileWriter fw = new FileWriter(configFile);
			fw.write(saveObj.toJSONString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class Config {
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

		private static boolean Fullbright = false;

		public static boolean isFullbright() {
			return Fullbright;
		}

		public static void setFullbright(boolean fullbright) {
			Fullbright = fullbright;
		}
	}
}
