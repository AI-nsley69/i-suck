package net.trainsley69.isuck;

import com.google.gson.Gson;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ISuck implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("i-suck");

	public static Gson gson = new Gson();
	public static Config config;

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
		}

		verifyConfig(config);

		LOGGER.info("Finished loading config");
	}

	private void verifyConfig(Config config) {
		if (config.XRay) config.XRay = false;
	}

	private void loadConfig() throws IOException {
		// Get the config file
		String fileName = "i-suck.json";
		Path configPath = FabricLoader.getInstance().getConfigDir();
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
			FileReader configReader = new FileReader(configFile);
			config = gson.fromJson(configReader, Config.class);
		}
	}

	public static void writeConfig() {
		// Config file
		String fileName = "i-suck.json";
		Path configPath = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir();
		File configFile = new File(configPath.toFile(), fileName);

		String content = ISuck.gson.toJson(ISuck.config, Config.class);

		try {
			FileWriter fw = new FileWriter(configFile);
			fw.write(content);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void reloadRenderer() {
		MinecraftClient client = MinecraftClient.getInstance();
		client.worldRenderer.reload();
	}

	public static class Config {
		public boolean AutoFish = false;

		public boolean Flying = false;

		public boolean Fullbright = false;

		public boolean AutoReplant = false;

		public boolean XRay = false;

        public boolean NoFog = false;

		public boolean NoAbuse = false;

		public boolean FastBreak = false;

		public boolean AutoTool = false;

		public boolean EntityGlow = false;
	}

	public static class Shared {
		public static int recastDelay = 0;

		public static int replantDelay = 0;

		public static class NoFog {
			public static float FOG_START = -8.0f;
			public static float FOG_END = 1_000_000.0f;

			public static class SUBMERSED {
				public static float FOG_START = -8.0f;
				public static float FOG_END = 1_000_000.0f;
			}
		}

		public static boolean justJoined = false;
	}
}
